# UPI Payment Service with SAGA Orchestration

A microservices-based UPI payment system implementing the **SAGA orchestration pattern** to manage distributed transactions. This project simulates a real-world payment flow, including user authentication, account validation, debit, credit, and notifications, with rollback capabilities for failures.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Microservices](#microservices)
- [Setup](#setup)
- [Running the Project](#running-the-project)
- [Testing](#testing)
- [SAGA Pattern](#saga-pattern)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

## Overview
The **UPI Payment Service** demonstrates a distributed payment system inspired by India's Unified Payments Interface (UPI). Built with Spring Boot microservices, it uses the SAGA orchestration pattern to ensure consistency across services. The system processes payments by coordinating authentication, account operations, and notifications, handling failures gracefully with compensating transactions.

**Key Features**:
- Distributed transaction management using SAGA orchestration.
- RESTful APIs for inter-service communication.
- In-memory H2 databases for each service.
- Idempotent operations to prevent duplicates.
- Error handling with rollback for failed transactions.

## Architecture
The system comprises four microservices, orchestrated by the Transaction Service:

1. **Transaction Service** (Port: 8080): Manages the payment SAGA, coordinating all steps.
2. **Authentication Service** (Port: 8081): Verifies user PINs.
3. **Account Service** (Port: 8082): Handles account validation, debit, and credit.
4. **Notification Service** (Port: 8083): Simulates sending payment notifications.

### Flow
1. A payment request is sent to the Transaction Service.
2. The Transaction Service orchestrates:
    - Authentication (verify PIN).
    - Validation (check balances and accounts).
    - Debit (deduct from sender).
    - Credit (add to recipient).
    - Notification (inform users).
3. If any step fails, compensating transactions (e.g., reverse debit) ensure consistency.

*Architecture diagram coming soon in `docs/architecture.png`.*

## Technologies
- **Java**: 21
- **Spring Boot**: 3.3.5
- **Spring Web**: REST APIs
- **Spring Data JPA**: Database operations
- **H2 Database**: In-memory storage
- **Lombok**: Boilerplate reduction
- **Maven**: Build tool

## Microservices
### Transaction Service
- **Endpoint**: `POST /api/payments`
- **Role**: Orchestrates the SAGA, tracks transaction state.
- **Database**: Stores transactions (`TRANSACTION` table).

### Authentication Service
- **Endpoint**: `POST /api/auth/verify`
- **Role**: Validates user PINs.
- **Database**: Stores credentials (`CREDENTIAL` table).

### Account Service
- **Endpoints**:
    - `POST /api/accounts/validate`
    - `POST /api/accounts/debit`
    - `POST /api/accounts/credit`
- **Role**: Manages accounts and transactions.
- **Database**: Stores accounts (`ACCOUNT`) and logs (`TRANSACTION_LOG`).

### Notification Service
- **Endpoint**: `POST /api/notifications/send`
- **Role**: Simulates notifications (console logs and database).
- **Database**: Stores notification logs (`NOTIFICATION_LOG`).

## Setup
### Prerequisites
- Java 21
- Maven 3.6+
- Git
- Postman or cURL (for testing)
- IDE (e.g., IntelliJ IDEA, VS Code)

### Clone the Repository
```
git clone https://github.com/Owaiseimdad/upi-payment-service.git
cd upi-payment-service
```


## SAGA Pattern
### The SAGA orchestration pattern ensures consistency across microservices:

Orchestrator: Transaction Service coordinates the flow.
Steps:
1. Authenticate (PIN verification). 
2. Validate (balance and account check). 
3. Debit (deduct from sender). 
4. Credit (add to recipient). 
5. Notify (send notifications).

Compensation: Failures trigger rollbacks (e.g., reverse debit if credit fails).
Idempotency: Unique transactionIds prevent duplicate operations.
Non-Critical Notification: Notification failures are logged but don’t rollback.

### Future Improvements
1. Async messaging with Kafka or RabbitMQ. 
2. Real notifications (email/SMS via Twilio). 
3. Secure APIs with JWT and hashed PINs. 
4. Persistent database (PostgreSQL/MySQL). 
5. Monitoring with Spring Actuator and Prometheus. 
6. Unit/integration tests with JUnit and MockMvc. 
7. Dockerized deployment