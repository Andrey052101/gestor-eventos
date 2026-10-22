# Gestor de Eventos

Aplicación web gestora de eventos desarrollada con Spring Boot como proyecto.

## Utilizado

- Java 21
- Spring Boot 3.2
- Spring MVC
- Thymeleaf
- AOP (Programación Orientada a Aspectos)
- Spring Data JPA
- MySQL
- Maven

## Estructura del proyecto

src/main/java/edu/instituto/gestor_eventos/
├── aspect/
│ └── LogAspect.java
├── controller/
│ ├── EventoController.java
│ ├── InscripcionController.java
│ └── UsuarioController.java
├── model/
│ ├── Evento.java
│ ├── EventoForm.java
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

(http://localhost:8080/registro)


Para registrar un nuevo evento:

(http://localhost:8080/eventos/nuevo)


## Avances:

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

### Semana 5 — Programación Orientada a Aspectos (AOP)
- Dependencia aspectjweaver agregada al pom.xml
- Paquete aspect creado
- Clase LogAspect con @Aspect y @Component
- Pointcut configurado sobre la capa de servicios
- @Before y @After interceptando métodos del servicio
- Logging transversal visible en consola al ejecutar /eventos

### Semana 7 — Spring MVC: Formularios y Validación
- Clase EventoForm.java con validaciones @NotBlank
- Formulario HTML nuevo-evento.html con Thymeleaf
- Ruta GET /eventos/nuevo para mostrar el formulario
- Ruta POST /eventos/nuevo para recibir y validar datos
- Validación con @Valid y BindingResult
- Mensajes de error en el formulario cuando hay campos vacíos
- Registro exitoso redirige a /eventos

### Semana 8 — Persistencia con Spring Data JPA y MySQL
- Dependencias Spring Data JPA y mysql-connector-j agregadas al pom.xml
- Entidad Evento.java mapeada con @Entity, @Id y @GeneratedValue
- Repositorio EventoRepository extendiendo de JpaRepository
- Configuración del Datasource y conexión a MySQL en application.properties
- Estrategia spring.jpa.hibernate.ddl-auto=update configurada
- EventoService actualizado para delegar la persistencia al repositorio JPA
- Flujo: Formulario → Controlador → Servicio → Repositorio → MySQL

### Semana 9 — CRUD completo con MySQL
- Operación Crear: formulario nuevo-evento.html con validaciones
- Operación Listar: listado de eventos desde MySQL en eventos.html
- Operación Editar: formulario editar-evento.html con datos precargados
- Operación Eliminar: eliminación directa desde el listado
- Botones Editar y Eliminar en cada card del listado
- Rutas GET y POST para editar: /eventos/editar/{id}
- Ruta GET para eliminar: /eventos/eliminar/{id}
- CRUD completo funcionando con persistencia real en MySQL

## Semana 10 – Validaciones, Búsqueda, Paginación y Relaciones

En esta fase del proyecto se han implementado mejoras clave en la arquitectura y la interfaz de usuario del Gestor de Eventos, cumpliendo con los requerimientos de la Actividad de la Semana 10:

### Características Implementadas
1. **Validaciones de Formularios:**
    - Incorporación de anotaciones de validación (`@NotBlank`, `@NotNull`, etc.) en las entidades.
    - Uso de `@Valid` y `BindingResult` en el controlador para interceptar datos incorrectos o vacíos.
    - Mensajes de error personalizados y destacados en color rojo en los formularios de creación y edición.

2. **Sistema de Búsqueda:**
    - Búsqueda dinámica de eventos por nombre utilizando consultas derivadas (`findByNombreContainingIgnoreCase`)[cite: 2].
    - Filtrado inteligente que soporta coincidencias parciales sin importar mayúsculas o minúsculas.
    - Botón de limpieza para restablecer la vista completa del listado.

3. **Paginación Dinámica:**
    - Distribución de registros por bloques (6 tarjetas por página) utilizando `Pageable` y `PageRequest`[cite: 2].
    - Controles de navegación profesionales (Anterior, Siguiente y numeración directa de páginas).
    - Manejo de estados vacíos cuando una categoría o búsqueda no arroja resultados.

4. **Relación entre Entidades:**
    - Configuración de una relación JPA `@ManyToOne` entre la entidad `Evento` y `Categoria`.
    - Selector dinámico de categorías integrado de forma fluida en los formularios del sistema.

5. **Mensajes al Usuario y Redireccionamiento:**
    - Uso de `RedirectAttributes` para enviar mensajes flash de éxito tras guardar o eliminar registros[cite: 2].
    - Patrón de redirección Post-Redirect-Get para evitar el reenvío duplicado de formularios.
    - Modal de confirmación flotante moderno para la eliminación segura de registros.

## Semana 11 – Sesiones, Cookies y Manejo de Errores

### Funcionalidades implementadas

- **Inicio de sesión** con `HttpSession` (nombre, contraseña)
- **Cierre de sesión** que invalida la sesión
- **Registro de usuarios** con validación
- **Roles**: ADMIN puede crear/editar/eliminar · USER solo visualiza
- **Interceptor** (`LoginInterceptor`) protege `/eventos` y `/preferencia`
- **Cookie de preferencia** de tema visual (claro/oscuro, 30 días)
- **Modo oscuro** con `localStorage` en el cliente
- **Errores personalizados**: 404, 400, 500 con Bootstrap
- **CRUD de eventos** con paginación, búsqueda y categorías

### Tecnologías
- Spring Boot 3.2.0, Thymeleaf, Spring Data JPA, MySQL
Navegador → EventoController → EventoService → EventoRepository → MySQL
↑
LogAspect (AOP)