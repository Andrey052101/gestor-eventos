# Gestor de Eventos

Aplicación web desarrollada con Spring Boot como proyecto
acumulativo de Programación IV.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1
- Spring MVC
- Thymeleaf
- Maven

## Estructura del proyecto

src/main/java/edu/instituto/gestor_eventos/
├── controller/
│ ├── EventoController.java
│ ├── InscripcionController.java
│ └── UsuarioController.java
├── model/
│ ├── Evento.java
│ ├── Inscripcion.java
│ └── Usuario.java
├── repository/
│ ├── EventoRepository.java
│ ├── InscripcionRepository.java
│ └── UsuarioRepository.java
└── service/
├── EventoService.java
├── InscripcionService.java
└── UsuarioService.java


## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

Abrir en el navegador:

http://localhost:8080/eventos


## Avances por semana

### Semana 1 — Configuración inicial
- Creación del proyecto con Spring Initializr
- Configuración de dependencias: Spring Web, Thymeleaf
- Estructura base del proyecto
- Archivo application.properties

### Semana 2 — Fundamentos de Spring
- Arquitectura por capas: Controller, Service, Model
- Creación de paquetes controller, model, service
- Creación del POJO Evento
- Anotaciones: @SpringBootApplication, @Service, @RestController

### Semana 3 — Configuración y dependencias
- Configuración de dependencias de aplicación web con Spring
- Implementación de repositorios
- Configuración del servidor en application.properties

### Semana 4 — Spring MVC y Thymeleaf
- Controlador MVC con @Controller y @GetMapping
- Separación de responsabilidades: Controller → Service → Model → View
- Vista dinámica con Thymeleaf (th:each, th:text)
- Ruta funcional: /eventos

## Arquitectura

Navegador → EventoController → EventoService → Evento → eventos.html


## Criterios de la práctica Semana 4

- @Controller con @GetMapping("/eventos")
- Model para pasar datos a la vista
- EventoService con método listar()
- Plantilla eventos.html en src/main/resources/templates/
- th:each y th:text para mostrar datos dinámicamente
  