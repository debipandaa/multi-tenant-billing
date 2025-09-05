# SaaS-Grade Metering & Billing Engine

This project is a complete, event-driven solution for tracking product usage, aggregating it into billable metrics, and generating invoices based on flexible, tiered pricing plans. It's designed to be a robust, scalable backend for any SaaS product that needs usage-based billing.

-----

## \#\# Core Features

  * **⚡ High-Throughput Usage Ingestion:** A non-blocking, reactive API (`Spring WebFlux`) for ingesting millions of usage events.
  * **🛡️ Resilient & Idempotent:** Uses **Kafka** as a durable event buffer and supports deduplication keys to safely handle retries.
  * **🔄 Real-time Aggregation:** An event-driven consumer that aggregates raw usage data into hourly windows, storing results in **PostgreSQL** and caching in **Redis**.
  * **💰 Flexible Pricing Engine:** A powerful, JSON-configurable pricing engine that supports complex strategies like **tiered pricing**.
  * **🤖 Automated Billing & Invoicing:** A scheduled cron job that automatically runs monthly billing, generating immutable invoices and downloadable **PDFs** using iText.
  * **📊 Web Dashboard:** A simple server-rendered dashboard built with **Thymeleaf** to view tenant usage and invoice history in real-time.

-----

## \#\# System Architecture

The system is designed around an event-driven, decoupled architecture to ensure scalability and resilience.

1.  **Ingestion:** Clients send usage events to the `Ingest API`. The API validates the event, checks for duplicates in Redis, and immediately publishes it to a `usage-events` Kafka topic.
2.  **Processing:** The `Kafka Consumer` listens to the topic. For each event, it performs two actions in parallel:
      * Saves the raw event to the `usage_events` table for auditing.
      * Triggers the `Aggregation Service` to update the hourly usage count in the `aggregates` table (Postgres) and the Redis cache.
3.  **Billing:** A scheduled `Billing Run Service` triggers monthly. It uses the `Pricing Engine` to calculate totals from the aggregated usage, then creates a permanent record in the `invoices` table and generates a PDF.
4.  **Presentation:** The `Dashboard Controller` reads data from Redis (for usage) and PostgreSQL (for invoices) to render a server-side HTML page with Thymeleaf.

<!-- end list -->

```plaintext
                                   +-----------------+
                                   |     Ingest API  |
                                   |   (WebFlux)     |
                                   +--------+--------+
                                            | (Usage Event)
                                            v
+-----------+                        +-----------------+
|   Redis   |<--(Dedupe & Cache)----|      Kafka      |----(Webhook/CDC)--> External Systems
| (Cache)   |                        | (usage-events)  |
+-----------+                        +--------+--------+
                                            |
           +--------------------------------v----------------------------------+
           |                         Kafka Consumer                           |
           +--------------------------------+----------------------------------+
                                            |
                  +-------------------------+-------------------------+
                  |                                                   |
                  v                                                   v
          +-----------------+                             +----------------------+
          |  Aggregation    |                             |  Raw Event Storage   |
          |  Service        |                             |  (usage_events)      |
          +-------+---------+                             +----------------------+
                  |
                  v
          +-----------------+
          |  Aggregates     |
          |  (Postgres)     |
          +-----------------+
```

-----

## \#\# Tech Stack

  * **Backend:** Spring Boot 3 (Java 17+), Spring WebFlux, Spring for Apache Kafka, Spring Data R2DBC
  * **Database:** PostgreSQL, Redis
  * **Messaging:** Apache Kafka
  * **Migrations:** Flyway
  * **PDF Generation:** iText 7
  * **Frontend:** Thymeleaf
  * **Build:** Maven

-----

## \#\# Getting Started

### \#\#\# Prerequisites

  * Java 17 or later
  * Maven 3.8+
  * Docker and Docker Compose

### \#\#\# 1. Set Up Infrastructure with Docker

This project includes a `docker-compose.yml` file to easily start all the required services (Postgres, Kafka, Redis, Zookeeper).

Create a `docker-compose.yml` file in the project root with the following content:

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.3.2
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.3.2
    container_name: kafka
    ports:
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://kafka:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1

  postgres:
    image: postgres:15
    container_name: postgres
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=your_user
      - POSTGRES_PASSWORD=your_password
      - POSTGRES_DB=billing

  redis:
    image: redis:7
    container_name: redis
    ports:
      - "6379:6379"
```

From your terminal in the project root, run:

```bash
docker-compose up -d
```

### \#\#\# 2. Configure the Application

Make sure your `src/main/resources/application.properties` file has the correct credentials for the services you just started.

```properties
# PostgreSQL R2DBC
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/billing
spring.r2dbc.username=your_user
spring.r2dbc.password=your_password

# Flyway (uses JDBC)
spring.flyway.url=jdbc:postgresql://localhost:5432/billing
spring.flyway.user=your_user
spring.flyway.password=your_password

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
# ... other kafka properties ...
```

### \#\#\# 3. Run the Application

You can now run the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

The application will start, and Flyway will automatically run all the database migrations to create the necessary tables.

-----

## \#\# API Endpoints

| Method | Endpoint                                         | Description                                    |
| :--- |:-------------------------------------------------| :--------------------------------------------- |
| `POST` | `/v1/usage`                                      | Ingests a raw usage event.                     |
| `POST` | `/api/v1/tenants`                                | Creates a new tenant.                          |
| `GET`  | `/api/v1/usage/tenants/{tenantId}`               | Gets aggregated usage for a tenant.            |
| `GET`  | `/api/v1/billing/tenants/{tenantId}/preview`     | Generates a preview of the current invoice.    |
| `POST` | `/api/v1/billing/admin/tenants/{tenantId}/generate-invoice` | Manually triggers an invoice run for a tenant. |
| `GET`  | `/dashboard/tenants/{tenantId}`                  | **(Browser)** Shows the web dashboard for a tenant. |
