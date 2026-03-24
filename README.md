# Tienda API 🛒

A production-grade store management REST API built with **Spring Boot 4.0**, featuring Clean Architecture, JWT authentication, Redis caching, email notifications, and CI/CD with GitHub Actions.

[![CI](https://github.com/Juliandav2/store/actions/workflows/ci.yml/badge.svg)](https://github.com/Juliandav2/store/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Redis](https://img.shields.io/badge/Redis-7-red)

---

## 📋 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Environment Variables](#environment-variables)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)

---

## ✨ Features

- ✅ **JWT Authentication** with Access Token + Refresh Token
- ✅ **Role-based Authorization** (ADMIN / USER)
- ✅ **CRUD** for Orders, Products and Customers
- ✅ **Order State Machine** (CREATED → CONFIRMED → PAID → CANCELLED / REFUNDED)
- ✅ **Order History** — tracks every state change with timestamp
- ✅ **Search & Filters** — filter products by name and price range, orders by state and customer
- ✅ **Pagination** on all list endpoints
- ✅ **Redis Cache** for products endpoint
- ✅ **Email Notifications** on registration and order state changes
- ✅ **Rate Limiting** — 60 requests per minute per IP
- ✅ **Input Validation** with detailed error messages
- ✅ **CORS** configured for frontend integration
- ✅ **Swagger UI** with JWT support
- ✅ **Health Check** via Spring Actuator
- ✅ **Database Migrations** with Flyway
- ✅ **136 tests** — unit + integration
- ✅ **CI/CD** with GitHub Actions

---

## 🏗️ Architecture

This project follows **Clean Architecture** principles, separating concerns into distinct layers:

```
┌─────────────────────────────────────┐
│           Controller Layer           │  ← HTTP requests/responses, DTOs
├─────────────────────────────────────┤
│            Service Layer             │  ← Facade, coordinates use cases
├─────────────────────────────────────┤
│           Use Case Layer             │  ← Business logic
├─────────────────────────────────────┤
│          Repository Layer            │  ← Data access interfaces
├─────────────────────────────────────┤
│         Infrastructure Layer         │  ← JPA, PostgreSQL, Redis
└─────────────────────────────────────┘
```

### Design Patterns Used

| Pattern | Where |
|---|---|
| **Strategy** | `DiscountStrategy` → `PremiumDiscount`, `RegularDiscount` |
| **Repository** | `OrderRepository` interface + `JpaOrderRepositoryAdapter` |
| **Facade** | `OrderService` coordinating multiple use cases |
| **Adapter** | `JpaOrderRepositoryAdapter` adapting JPA to domain interface |
| **Chain of Responsibility** | Spring Security filter chain |

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.0 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Database | PostgreSQL 16 |
| ORM | Hibernate / Spring Data JPA |
| Migrations | Flyway |
| Cache | Redis 7 |
| Email | Spring Mail (Gmail SMTP) |
| Rate Limiting | Bucket4j 8.10.1 |
| Documentation | Swagger / SpringDoc OpenAPI |
| Monitoring | Spring Actuator |
| Testing | JUnit 5 + Mockito |
| CI/CD | GitHub Actions |
| Containerization | Docker / Docker Compose |

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Docker & Docker Compose
- Maven

### Running with Docker Compose

```bash
# Clone the repository
git clone https://github.com/Juliandav2/store.git
cd store

# Start all services (PostgreSQL + Redis + App)
docker-compose up -d
```

### Running locally

```bash
# Start PostgreSQL and Redis
docker run -d --name tienda-db -e POSTGRES_DB=tienda -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin123 -p 5432:5432 postgres:16
docker run -d --name tienda-redis -p 6379:6379 redis:7

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 📚 API Documentation

Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

### Authentication

All endpoints except `/auth/**` require a JWT token in the Authorization header:
```
Authorization: Bearer <your-token>
```

### Endpoints Overview

#### Auth
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/auth/register` | Register a new user | ❌ |
| POST | `/auth/login` | Login and get tokens | ❌ |
| POST | `/auth/refresh` | Refresh access token | ❌ |
| POST | `/auth/logout` | Revoke refresh token | ❌ |

#### Products
| Method | Endpoint | Description | Role |
|---|---|---|---|
| GET | `/products` | List products (paginated, filterable) | USER |
| GET | `/products/{id}` | Get product by ID | USER |
| POST | `/products` | Create product | ADMIN |
| PUT | `/products/{id}/price` | Update product price | ADMIN |
| DELETE | `/products/{id}` | Delete product | ADMIN |

#### Customers
| Method | Endpoint | Description | Role |
|---|---|---|---|
| GET | `/customers` | List customers (paginated) | USER |
| GET | `/customers/{id}` | Get customer by ID | USER |
| POST | `/customers` | Create customer | ADMIN |
| DELETE | `/customers/{id}` | Delete customer | ADMIN |

#### Orders
| Method | Endpoint | Description | Role |
|---|---|---|---|
| GET | `/orders` | List orders (paginated, filterable) | USER |
| POST | `/orders` | Create order | USER |
| POST | `/orders/products` | Add product to order | USER |
| PATCH | `/orders/{id}/confirm` | Confirm order | USER |
| PATCH | `/orders/{id}/pay` | Pay order | USER |
| PATCH | `/orders/{id}/cancel` | Cancel order | USER |
| PATCH | `/orders/{id}/refund` | Refund order | USER |
| GET | `/orders/{id}/history` | Get order state history | USER |

#### Monitoring
| Method | Endpoint | Description |
|---|---|---|
| GET | `/actuator/health` | Application health check |

### Query Parameters

**Products:**
```
GET /products?page=0&size=10&name=laptop&minPrice=100&maxPrice=2000
```

**Orders:**
```
GET /orders?page=0&size=10&state=CONFIRMED&customerId=abc123
```

---

## ⚙️ Environment Variables

| Variable | Description | Default |
|---|---|---|
| `DB_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/tienda` |
| `DB_USERNAME` | Database username | `admin` |
| `DB_PASSWORD` | Database password | `admin123` |
| `JWT_SECRET` | JWT signing secret (min 32 chars) | — |
| `JWT_EXPIRATION` | Access token expiration in ms | `86400000` (24h) |
| `REDIS_HOST` | Redis host | `localhost` |
| `REDIS_PORT` | Redis port | `6379` |
| `MAIL_USERNAME` | Gmail address | — |
| `MAIL_PASSWORD` | Gmail app password | — |
| `FRONTEND_URL` | Frontend URL for CORS | `http://localhost:3000` |

---

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run only unit tests
mvn test -Dtest="**/usecases/**"

# Run only integration tests
mvn test -Dtest="*RepositoryTest"
```

### Test Coverage

| Type | Classes | Tests |
|---|---|---|
| Unit (Use Cases) | 4 | 12 |
| Unit (Controllers) | 1 | 11 |
| Unit (Services) | 1 | 12 |
| Integration (Repositories) | 3 | 11 |
| Integration (JWT) | 1 | 4 |
| **Total** | **10** | **136** |

---

## 📁 Project Structure

```
src/
├── main/java/com/tienda/
│   ├── app/                    # Spring configuration (AppConfig)
│   ├── application/            # Use cases (business logic)
│   ├── controller/             # REST controllers + GlobalExceptionHandler
│   ├── discount/               # Discount strategies
│   ├── dto/                    # Request/Response DTOs
│   ├── exception/              # Domain exceptions
│   ├── mapper/                 # Entity-DTO mappers
│   ├── model/                  # Domain entities
│   ├── repository/             # Repository interfaces + adapters
│   ├── security/               # JWT, filters, security config
│   └── service/                # Service facades
├── main/resources/
│   ├── db/migration/           # Flyway SQL migrations (V1-V4)
│   └── application.properties
└── test/
    ├── java/com/tienda/
    │   ├── usecases/           # Use case unit tests
    │   └── controller/         # Controller unit tests
    └── resources/
        └── application.properties
```

---

## 🗄️ Database Schema

```
customers          orders             order_items
─────────          ──────             ───────────
id (PK)            id (PK)            id (PK)
type               customer_id (FK)   order_id (FK)
name               state              product_id (FK)
                                      amount
products                              unit_price
────────
id (PK)            order_history      users
name               ─────────────      ─────
price              id (PK)            id (PK)
                   order_id (FK)      username
refresh_tokens     state              password
──────────────     changed_at         role
id (PK)
token
username
expires_at
revoked
```

---

## 📝 License

This project is for educational and portfolio purposes.

---

Built with by [Julian](https://github.com/Juliandav2)