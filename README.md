
# SaaS Billing System

A **multi-tenant SaaS billing platform** built with **Spring Boot**, **Hibernate**, and **MySQL**. This project allows SaaS providers to track usage, calculate invoices based on customizable pricing plans, and generate downloadable PDFs for each tenant.

---

## Features

- **Multi-Tenant Support** – Separate billing and usage for each tenant.
- **Custom Pricing Plans** – Define tiered or per-unit pricing for API calls, storage, emails, etc.
- **Aggregated Usage** – Automatically aggregate usage metrics for billing periods.
- **Invoice Generation** – Generate and download invoices as PDF files.
- **Scheduler** – Automatically calculate invoices periodically.
- **REST APIs** – CRUD operations for tenants, plans, usage events, and invoices.
- **Database Migrations** – Flyway-based SQL migrations for easy setup.
- **Exception Handling** – Centralized error handling with meaningful API responses.

---

## Technology Stack

- **Backend:** Java 21, Spring Boot 3.2
- **Database:** MySQL 8+, Hibernate ORM
- **Build Tool:** Maven
- **PDF Generation:** Custom PDF utility
- **Testing:** JUnit 5 (unit and integration tests)
- **API Documentation:** Swagger/OpenAPI

---

## Folder Structure
```
src/
├─ main/
│   ├─ java/com/example/billing
│   │   ├─ config/          # Application, Security, Swagger configs
│   │   ├─ controller/      # REST controllers
│   │   ├─ dto/             # Request/response DTOs
│   │   ├─ entity/          # JPA entities
│   │   ├─ exception/       # Custom exceptions & handlers
│   │   ├─ repository/      # Spring Data JPA repositories
│   │   ├─ scheduler/       # Scheduled billing jobs
│   │   ├─ service/         # Business logic & pricing
│   │   └─ util/            # PDF and JSON utilities
│   └─ resources/
│       ├─ db/migration/    # Flyway SQL migrations
│       ├─ application.yml  # Configuration
│       └─ templates/
└─ test/
└─ java/com/example/billing
├─ controller/
├─ integration/
└─ service/

```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven
- MySQL
- Git

### Setup

1. **Clone the repository:**

```bash
git clone <repo-url>
cd saas-billing
````

2. **Configure database:**

Update `src/main/resources/application.yml` with your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/billingdb
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: validate
```

3. **Run database migrations:**

```bash
mvn flyway:migrate
```

4. **Build and run the application:**

```bash
mvn clean package
java -jar target/billing-0.0.1-SNAPSHOT.jar
```

---

## API Endpoints

| Resource     | Endpoint            | Method              | Description                  |
| ------------ | ------------------- | ------------------- | ---------------------------- |
| Tenants      | `/api/tenants`      | GET/POST/PUT/DELETE | Tenant management            |
| Plans        | `/api/plans`        | GET/POST/PUT/DELETE | Pricing plan management      |
| Usage Events | `/api/usage-events` | GET/POST            | Record and view usage        |
| Invoices     | `/api/invoices`     | GET/POST            | Generate & download invoices |

**Swagger UI** available at: `http://localhost:8080/swagger-ui/index.html`

---

## Testing

Run unit and integration tests:

```bash
mvn test
```

