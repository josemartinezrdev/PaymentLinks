# Payment Links Service

Microservicio para la gestión de enlaces de pago, desarrollado con **Java 17** y **Spring Boot 3** siguiendo una arquitectura **Hexagonal** con enfoque **Vertical Slicing**.

### Tecnologías

- **Java 17**
- **Spring Boot 3**
  - Spring Web
  - Spring Data JPA
  - Spring Security (JWT)
  - Springdoc OpenAPI UI (Swagger)
- **MySQL 8** (Docker)
- **phpMyAdmin** (Docker)
- **Maven** como gestor de dependencias

### Instalación

- Requisitos previos
  - Docker
  - Docker Compose

- Clonar el repositorio

  - git clone https://github.com/josemartinezrdev/PaymentLinks
  - cd PaymentLinks

- Construir el proyecto

  - Desde la raíz: docker-compose up --build

- Acceder a la documentación

  - http://localhost:8080/swagger-ui.html

- Acceder a la documentación de phpMyAdmin

  - http://localhost:8081/
