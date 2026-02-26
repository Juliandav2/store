Store — Order Management System
A backend application for managing orders built in pure Java, designed with Clean Architecture and ready to migrate to Spring Boot.

Architecture
This project follows Clean Architecture principles with a clear separation of layers:
src/
├── model/          → Domain entities and business rules
│   ├── Order.java              (aggregate root with state machine)
│   ├── Customer.java           (abstract base)
│   ├── PremiumCustomer.java
│   ├── RegularCustomer.java
│   ├── Product.java
│   └── ItemOrder.java
│
├── discount/       → Strategy Pattern for discounts
│   ├── DiscountStrategy.java   (interface)
│   ├── NoDiscount.java         (0%)
│   ├── RegularDiscount.java    (5%)
│   └── PremiumDiscount.java    (10%)
│
├── exception/      → Domain exceptions hierarchy
│   ├── BusinessException.java  (base)
│   ├── EmptyOrderException.java
│   ├── InvalidOrderStateException.java
│   └── OrderNotFoundException.java
│
├── repository/     → Persistence abstraction
│   ├── OrderRepository.java        (interface / port)
│   └── InMemoryOrderRepository.java
│
├── dto/            → Data Transfer Objects
│   ├── CreateOrderRequest.java
│   ├── AddProductRequest.java
│   └── OrderResponse.java
│
├── mapper/         → Domain ↔ DTO transformation
│   └── OrderMapper.java
│
├── application/    → Use cases (one class per operation)
│   ├── CreateOrderUseCase.java
│   ├── AddProductOrderUseCase.java
│   ├── ConfirmOrderUseCase.java
│   ├── PayOrderUseCase.java
│   ├── CancelOrderUseCase.java
│   └── RefundOrderUseCase.java
│
├── service/        → Application facade
│   └── OrderService.java
│
├── controller/     → Entry point (future REST controller)
│   └── OrderController.java
│
└── Main.java       → Demo of the full order lifecycle

Order Lifecycle
CREATED ──→ CONFIRMED ──→ PAID ──→ SENT ──→ DELIVERED
│              │
└──────────────┴──→ CANCELED   (via cancel())
↑
PAID/SENT (via refund())
Business rules:

Items can only be added in CREATED state
An order must have at least one item to be confirmed
An order must be CONFIRMED before payment
Only CREATED or CONFIRMED orders can be canceled
Only PAID or SENT orders can be refunded


Discount Strategy
Customer TypeDiscountRegular5%Premium10%No discount0%
Discounts are applied via the Strategy Pattern — each customer type carries its own DiscountStrategy. The discounted total is always returned in OrderResponse.

How to run
Requirements

Java 21+
Maven 3.8+

Run the demo
bashmvn compile
mvn exec:java -Dexec.mainClass="com.tienda.Main"
Run the tests
bashmvn test

Test Coverage
LayerTest classDomainOrderTest, ProductTest, ItemOrderTestDiscountDiscountStrategyTestRepositoryInMemoryOrderRepositoryTestMapperOrderMapperTestUse casesCreateOrderUseCaseTest, AddProductOrderUseCaseTest, ConfirmOrderUseCaseTest, PayOrderUseCaseTest, CancelOrderUseCaseTest, RefundOrderUseCaseTestServiceOrderServiceTestControllerOrderControllerTest

Spring Boot Migration Plan
This project is intentionally structured to make the Spring migration straightforward:
Now (pure Java)After migration (Spring Boot)OrderController@RestController + @RequestMappingOrderService@ServiceOrderRepository (interface)extends JpaRepositoryInMemoryOrderRepositoryJpaOrderRepository implementationManual constructor injection@Autowired / constructor injectionIllegalArgumentException@Valid + @ExceptionHandler

Design Patterns Used

Strategy — discount policies per customer type
Repository — persistence abstraction decoupled from domain
Use Case / Interactor — one class per business operation
Facade — OrderService simplifies access to use cases
DTO — request/response objects that protect the domain model


Author
Julian — aspiring Java backend developer.
GitHub 