# Saga-Knot: Event-Driven E-Commerce Platform

[![Java](https://img.shields.io/badge/Java-17%2F21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Axon Framework](https://img.shields.io/badge/Axon-4.9.3-blue.svg)](https://www.axoniq.io/)
[![React](https://img.shields.io/badge/React-19.1.1-61dafb.svg)](https://reactjs.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Under%20Construction-orange.svg)](https://github.com)

> **⚠️ PROJECT STATUS: UNDER CONSTRUCTION**
> This project is actively being developed and is not yet production-ready. Some features may be incomplete or subject to significant changes.

## 📖 Overview

**Saga-Knot** is a sophisticated, enterprise-grade **e-commerce microservices platform** built with modern distributed systems architecture patterns. The platform demonstrates advanced implementation of:

- **Event-Driven Architecture (EDA)** using Axon Framework
- **CQRS (Command Query Responsibility Segregation)**
- **Event Sourcing** for complete audit trails
- **Saga Pattern** for distributed transaction orchestration
- **Domain-Driven Design (DDD)** with bounded contexts
- **Polyglot Persistence** (MySQL, MongoDB, Redis)

The project serves as both a learning resource and a foundation for building scalable, resilient e-commerce systems.

---

## 🏗️ Architecture

### High-Level Architecture

```
┌─────────────┐
│   React UI  │
└──────┬──────┘
       │
┌──────▼───────────────────────────────────────┐
│        API Gateway (OAuth2/Keycloak)        │
└──────┬───────────────────────────────────────┘
       │
       ├─────┬─────┬─────┬─────┬─────┬─────┐
       │     │     │     │     │     │     │
    User  Product Order Cart Payment Saga
    Service Service Service Service Service Orchestrator
       │     │     │     │     │     │
    MySQL  MySQL  MongoDB MongoDB Stateless
   (Master (Master
   -Slave) -Slave)
```

### Key Architectural Patterns

- **Service-per-Database Pattern**: Each microservice owns its database
- **Event Sourcing**: All state changes captured as immutable events
- **CQRS**: Separate read and write models for optimized performance
- **Saga Pattern**: Coordinated distributed transactions with compensation logic
- **API Gateway Pattern**: Single entry point with authentication/authorization
- **Service Discovery**: Dynamic service registration with Eureka
- **Centralized Configuration**: Spring Cloud Config Server

---

## 🛠️ Technology Stack

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17/21 | Programming Language |
| **Spring Boot** | 3.5.6 | Microservices Framework |
| **Spring Cloud** | 2025.0.0 | Distributed Systems Toolkit |
| **Axon Framework** | 4.9.3 | CQRS & Event Sourcing |
| **Axon Server** | 2024.2.15 | Event Store & Message Router |

### Databases & Persistence

| Technology | Use Case | Services |
|------------|----------|----------|
| **MySQL** | Transactional Data (Master-Slave) | User, Product |
| **MongoDB** | High-throughput Writes | Cart, Order |
| **Redis Sentinel** | Distributed Caching (HA) | User Service Cache |

### Messaging & Streaming

| Technology | Purpose |
|------------|---------|
| **Apache Kafka** | Event Streaming |
| **Debezium** | Change Data Capture (CDC) |
| **RabbitMQ** | Spring Cloud Bus |

### Infrastructure

| Technology | Purpose |
|------------|---------|
| **Keycloak** | Identity & Access Management |
| **Eureka** | Service Registry |
| **Spring Cloud Gateway** | API Gateway |
| **Docker Compose** | Local Development Environment |
| **Kubernetes** | Container Orchestration |

### Observability

| Technology | Purpose |
|------------|---------|
| **Prometheus** | Metrics Collection |
| **Zipkin** | Distributed Tracing |
| **Spring Boot Admin** | Service Monitoring |
| **Micrometer** | Application Metrics |

### Frontend

| Technology | Version | Purpose |
|------------|---------|---------|
| **React** | 19.1.1 | UI Framework |
| **Vite** | 7.1.7 | Build Tool |
| **Bootstrap** | 5.3.8 | CSS Framework |

---

## 📦 Project Structure

```
Saga-knot/
├── parent/                     # Core Microservices
│   ├── user/                   # User & Address Management
│   ├── product/                # Product Catalog (Products, Brands, Categories)
│   ├── order/                  # Order Processing
│   ├── cart/                   # Shopping Cart
│   ├── payment/                # Payment Processing
│   └── saga/                   # Saga Orchestration
├── common/                     # Shared Libraries (DTOs, Entities, Exceptions)
├── gateway/                    # API Gateway
├── discovery/                  # Eureka Service Registry
├── configs/                    # Config Server
├── administration/             # Spring Boot Admin
├── docker/                     # Docker Compose Infrastructure
├── kubernetes/                 # K8s Deployment Manifests
├── view/                       # React Frontend
└── documentation/              # Architecture Documentation
```

---

## 🔧 Microservices Overview

### Core Business Services

| Service | Port | Database | Description |
|---------|------|----------|-------------|
| **User Service** | 9098 | MySQL (Master-Slave) | User management, contact information, addresses. CQRS with Redis caching. |
| **Product Service** | 8071 | MySQL (Master-Slave) | Product catalog, brands, categories. Event-sourced with Axon. |
| **Order Service** | 8072 | MongoDB | Order processing and lifecycle management. Event-driven workflows. |
| **Cart Service** | 9012 | MongoDB | Shopping cart operations. Optimized for high-write workloads. |
| **Payment Service** | 9050 | Stateless | Payment transaction processing. Integration with payment gateways. |
| **Saga Service** | 9020 | N/A | Distributed transaction orchestration across services. |

### Infrastructure Services

| Service | Port | Description |
|---------|------|-------------|
| **Config Server** | 8090 | Centralized configuration management |
| **Discovery (Eureka)** | 8761 | Service registry and discovery |
| **Gateway** | 8082 | API Gateway with OAuth2/Keycloak |
| **Administration** | TBD | Spring Boot Admin dashboard |

---

## 🚀 Getting Started

### Prerequisites

- **Java**: JDK 17 or 21
- **Maven**: 3.8+
- **Docker**: 20.10+
- **Docker Compose**: 2.0+
- **Node.js**: 18+ (for frontend)
- **npm**: 9+ (for frontend)

### Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/saga-knot.git
cd saga-knot
```

#### 2. Start Infrastructure with Docker Compose

```bash
cd docker
docker-compose up -d
```

This will start:
- MySQL (master + slave)
- MongoDB
- Redis Sentinel (1 master + 3 slaves + 3 sentinels)
- Kafka + Zookeeper
- Axon Server
- Keycloak
- RabbitMQ
- LocalStack (AWS S3 emulation)
- Monitoring tools (Prometheus, Conduktor Console)

**Wait for all services to be healthy** (~2-3 minutes):

```bash
docker-compose ps
```

#### 3. Build Parent Modules

```bash
# Build common module first
cd ../common
mvn clean install

# Build all microservices
cd ../parent
mvn clean install
```

#### 4. Start Microservices

**Option A: Using Maven**

Open separate terminal windows for each service:

```bash
# Config Server (start first)
cd configs
mvn spring-boot:run

# Discovery Server (start second)
cd discovery
mvn spring-boot:run

# Wait for Config & Discovery to be ready, then start business services:

cd parent/user
mvn spring-boot:run

cd parent/product
mvn spring-boot:run

cd parent/order
mvn spring-boot:run

cd parent/cart
mvn spring-boot:run

cd parent/payment
mvn spring-boot:run

cd parent/saga
mvn spring-boot:run

# Gateway (start last)
cd gateway
mvn spring-boot:run
```

**Option B: Using Docker (if images are built)**

```bash
# Build images with Jib
cd parent
mvn compile jib:dockerBuild

# Update docker-compose to include microservices
docker-compose -f docker-compose-full.yml up -d
```

#### 5. Start Frontend

```bash
cd view
npm install
npm run dev
```

Frontend will be available at: http://localhost:5173

---

## 🔑 Access Endpoints

### Services

| Service | URL | Credentials |
|---------|-----|-------------|
| **API Gateway** | http://localhost:8082 | Via Keycloak |
| **Eureka Dashboard** | http://localhost:8761 | - |
| **Config Server** | http://localhost:8090 | - |
| **Spring Boot Admin** | TBD | - |
| **Keycloak Admin** | http://localhost:9030 | admin/admin |
| **Axon Server** | http://localhost:8024 | - |
| **RabbitMQ Management** | http://localhost:15672 | guest/guest |
| **Conduktor Console** | http://localhost:8080 | - |

### Keycloak Configuration

- **Realm**: `SagaDev`
- **Client ID**: `gateway-knot`
- **Client Secret**: (configured in gateway.yml)

---

## 📊 CQRS & Event Sourcing Implementation

### Command Side (Write Model)

```java
// Example: Creating a Brand
CreateBrandCommand command = CreateBrandCommand.builder()
    .idBrand(UUID.randomUUID().toString())
    .name("Nike")
    .description("Sports brand")
    .build();

commandGateway.send(command);
```

### Event Flow

```
Command → CommandInterceptor (validation) → Aggregate → Event
    ↓
Event Store (Axon Server)
    ↓
Event Handlers (Projections)
    ↓
Read Models (JPA Entities)
```

### Query Side (Read Model)

```java
// Example: Querying Brands
@QueryHandler
public List<BrandDto> handle(FindAllBrandsQuery query) {
    return brandRepository.findAll()
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
}
```

---

## 🧪 Testing

### Run Unit Tests

```bash
cd parent
mvn test
```

### Run Integration Tests

```bash
mvn verify
```

### Test Coverage

- Unit tests with JUnit 5
- Integration tests with Testcontainers
- Axon Test Framework for CQRS/ES testing

---

## 📈 Monitoring & Observability

### Spring Boot Actuator

All services expose actuator endpoints:

```
http://localhost:<service-port>/actuator/health
http://localhost:<service-port>/actuator/metrics
http://localhost:<service-port>/actuator/prometheus
```

### Distributed Tracing

Zipkin collects traces from all services. View at: http://localhost:9411

### Metrics

Prometheus scrapes metrics from all services. Configure Grafana dashboards for visualization.

---

## 🗂️ Database Schemas

### MySQL (User & Product Services)

```sql
-- User Service Tables
users
addresses
address_lookup
contact_lookup

-- Product Service Tables
products
brands
categories
product_lookup
brand_lookup
category_lookup
```

### MongoDB (Cart & Order Services)

```javascript
// Cart Service Collections
carts
cart_items

// Order Service Collections
orders
order_items
```

---

## 🔄 CI/CD

### GitHub Actions

- **Build & Test**: On every push and PR
- **SonarQube Analysis**: Code quality checks
- **Maven Caching**: For faster builds

```yaml
# .github/workflows/build.yml
name: Build & Test
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'zulu'
      - name: Build with Maven
        run: mvn clean install
      - name: SonarQube Scan
        run: mvn sonar:sonar
```

---

## 🛣️ Roadmap

### ✅ Completed

- [x] Core microservices architecture
- [x] CQRS & Event Sourcing with Axon
- [x] Saga orchestration setup
- [x] MySQL master-slave replication
- [x] Redis Sentinel high availability
- [x] Docker Compose infrastructure
- [x] Keycloak authentication
- [x] Service discovery with Eureka
- [x] Command Interceptors for validation
- [x] Lookup tables for duplicate prevention
- [x] GitHub Actions CI pipeline

### 🚧 In Progress

- [ ] Query Handlers implementation
- [ ] Frontend components development
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Integration tests for Sagas
- [ ] End-to-end testing

### 📋 Planned

- [ ] GraphQL API layer
- [ ] Kubernetes deployment automation
- [ ] Grafana dashboards
- [ ] ELK Stack for log aggregation
- [ ] Performance testing (Gatling)
- [ ] Circuit breakers (Resilience4j)
- [ ] API rate limiting
- [ ] Notification service (email/SMS)
- [ ] Search service (Elasticsearch)
- [ ] Recommendation engine
- [ ] Admin dashboard
- [ ] Mobile app (React Native)

---

## 📚 Documentation

- [Architecture Documentation](documentation/Documentation.txt)
- [API Documentation](docs/api.md) *(coming soon)*
- [Development Guide](docs/development.md) *(coming soon)*
- [Deployment Guide](docs/deployment.md) *(coming soon)*
- [Troubleshooting](docs/troubleshooting.md) *(coming soon)*

---

## 🤝 Contributing

Contributions are welcome! This project is under active development.

### How to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Coding Standards

- Follow Java coding conventions
- Write unit tests for new features
- Update documentation as needed
- Use meaningful commit messages

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Luis Piquin Rey**

- GitHub: [@luispiquinrey](https://github.com/luispiquinrey)
- Email: luispiquinrey@example.com

---

## 🙏 Acknowledgments

- **Axon Framework** for excellent CQRS/ES support
- **Spring Boot** team for the amazing framework
- **Netflix OSS** for Eureka
- Open source community for inspiration

---

## ⚡ Quick Start Commands

```bash
# Start infrastructure
cd docker && docker-compose up -d

# Build project
cd ../parent && mvn clean install

# Start all services (in separate terminals)
cd configs && mvn spring-boot:run
cd discovery && mvn spring-boot:run
cd parent/user && mvn spring-boot:run
cd parent/product && mvn spring-boot:run
cd gateway && mvn spring-boot:run

# Start frontend
cd view && npm install && npm run dev
```

---

## 📞 Support

If you have questions or need help:

- Open an issue on GitHub
- Check the [documentation](documentation/)
- Review existing issues and PRs

---

**⚠️ Remember: This project is under active development. Not all features are complete or stable. Use at your own risk in non-production environments.**

---

<p align="center">
  Made with ❤️ and ☕ by Luis Piquin Rey
</p>
