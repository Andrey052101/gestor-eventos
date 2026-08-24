# Gestor de Eventos — Spring Boot

Proyecto académico desarrollado con Spring Boot como parte del curso de desarrollo web.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.0
- Spring Web (spring-boot-starter-web)
- Thymeleaf (spring-boot-starter-thymeleaf)
- Spring Validation (spring-boot-starter-validation)
- Spring DevTools (spring-boot-devtools)
- Maven Wrapper

## Configuración del proyecto

### Requisitos previos

- JDK 21 instalado
- Git instalado

### Clonar el repositorio

```bash
git clone https://github.com/Andrey052101/gestor-eventos.git
cd gestor-eventos
```

### Compilar el proyecto

```bash
.\mvnw.cmd clean package
```

### Ejecutar la aplicación

```bash
.\mvnw.cmd spring-boot:run
```

La aplicación inicia en: http://localhost:8080

## Configuración application.properties

```properties
spring.application.name=gestor-eventos
server.port=8080
spring.profiles.active=dev
logging.level.edu.instituto=DEBUG
```

## Estructura del proyecto

gestor-eventos/
├── src/
│ └── main/
│ ├── java/
│ │ └── edu/instituto/gestor_eventos/
│ └── resources/
│ └── application.properties
├── .mvn/wrapper/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md


## Semana 03 — Lo que se configuró

- Dependencias Maven corregidas y actualizadas en pom.xml
- application.properties configurado con nombre de app, puerto 8080, perfil dev y nivel DEBUG
- Verificación de Maven Wrapper disponible
- Compilación exitosa con mvnw clean package
- Aplicación ejecutándose correctamente en puerto 8080