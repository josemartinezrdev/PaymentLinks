# Payment Links Service

Microservicio para la gestión de enlaces de pago, desarrollado con **Java 17** y **Spring Boot 3** siguiendo una arquitectura **Hexagonal** con enfoque **Vertical Slicing**.

### Tecnologías

* **Java 17**
* **Spring Boot 3**

  * Spring Web
  * Spring Data JPA
  * Spring Security (JWT)
  * Springdoc OpenAPI UI (Swagger)
* **MySQL 8** (Docker)
* **phpMyAdmin** (Docker)
* **Maven** como gestor de dependencias

### Instalación

* Requisitos previos

  * Docker
  * Docker Compose

* Clonar el repositorio

  ```bash
  git clone https://github.com/josemartinezrdev/PaymentLinks
  cd PaymentLinks
  ```

* Construir el proyecto

  ```bash
  # Levantar los contenedores
  docker-compose up -d

  # Inicializar la base de datos con datos precargados
  docker exec -i mysql-payment mysql -uroot -proot123456 dbpaymentslinks < init.sql
  ```

### Acceder a la documentación

* Swagger UI de los endpoints:

  ```
  http://localhost:8080/swagger-ui.html
  ```

* PhpMyAdmin para gestionar la base de datos:

  ```
  http://localhost:8081/
  ```

### Postman

* Se añade al proyecto el archivo `postman.json` listo para importar en Postman para tener ejemplos las llamadas a los endpoints más importantes y probarlos rápidamente.
