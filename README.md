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

## Semana 13 – API CRUD de Eventos

Convertí la gestión de eventos de mi proyecto en un servicio web. Con Postman se pueden consultar, crear, modificar y eliminar eventos, y la API responde en JSON. Usa el mismo `EventoService` y la misma base de datos del proyecto.

### Endpoints

| Método | Ruta | Qué hace | Códigos |
|---|---|---|---|
| GET | `/api/eventos` | Lista todos | 200 |
| GET | `/api/eventos/{id}` | Busca uno | 200, 404 |
| POST | `/api/eventos` | Crea | 200, 400 |
| PUT | `/api/eventos/{id}` | Modifica | 200, 400, 404 |
| DELETE | `/api/eventos/{id}` | Elimina | 200, 404 |

200 es éxito, 400 es dato inválido y 404 es evento inexistente.

### Ejemplos

**POST `/api/eventos`**
```json
{
  "nombre": "Feria de Tecnología",
  "descripcion": "Exposición de proyectos de software",
  "fecha": "2026-12-05",
  "lugar": "Auditorio Central",
  "categoria": { "id": 1 }
}
```
Respuesta 200: el evento creado, con su `id`.

**GET `/api/eventos/32`** (ya eliminado) → 404
```json
{ "error": "No existe un evento con id 32" }
```

**POST con campos vacíos** → 400
```json
{ "error": "Datos inválidos", "detalles": { "nombre": "El nombre es obligatorio" } }
```

### Evidencias en Postman

Capturas en la carpeta `evidencias/`: GET, POST, GET por id, PUT, DELETE, error 404 y dos errores 400.
Colección: `Semana13.postman_collection.json`.

## Semana 14 – Estándares REST en la API de Eventos

En esta semana mejoré la API de la semana 13 para aplicar buenas prácticas REST, separando la lógica y organizando mejor las respuestas.

### Endpoints principales
* **GET `/api/eventos`**: Devuelve la lista completa (200 OK).
* **GET `/api/eventos/{id}`**: Busca por ID (200 OK o 404 si no existe).
* **POST `/api/eventos`**: Crea un evento nuevo y devuelve la cabecera `Location` (201 Created).
* **PUT `/api/eventos/{id}`**: Modifica un evento existente (200 OK).
* **DELETE `/api/eventos/{id}`**: Elimina el evento (204 No Content).

### Qué cambios hice
* Separé el código en **Controller** (para manejar las rutas) y **Service** (para la lógica de negocio y validaciones).
* Usé **DTOs** (`EventoRequest` para lo que envía el cliente y `EventoResponse` para lo que devuelve).
* Agregué validaciones en los DTOs para que los campos obligatorios y las fechas cumplan con el formato correcto (`AAAA-MM-DD`).
* Implementé un `@RestControllerAdvice` para capturar errores y devolver respuestas limpias en formato JSON con códigos HTTP adecuados (400, 404, 500).

## Semana 15 – Programación reactiva con Spring (Flux y Mono)

Esta semana le agregué a mi Gestor de Eventos una parte reactiva que funciona solo desde la consola. No creé endpoints nuevos ni usé WebFlux, y la API REST de las semanas anteriores quedó igual y sigue funcionando.

Para esto agregué la dependencia `reactor-core` al `pom.xml` y creé dos clases en el paquete `reactivo`:

- **EventoReactivoService**: es el servicio con `@Service`. Ahí armé los flujos con `Flux` y `Mono`.
- **EventoReactivoRunner**: se ejecuta cuando arranca la aplicación y va mostrando en la consola cada prueba.

### Los datos que usé
Hice 5 eventos de ejemplo, que no se guardan en la base de datos. Decidí que un evento está activo (vigente) si su fecha es de hoy o de un día futuro. El "Torneo de robotica" ya pasó, entonces ese es mi registro inactivo.

### Cómo usé los operadores
Con `filter()` dejo solo los eventos vigentes. Con `map()` convierto cada evento en una frase con el nombre, la categoría, el lugar y la fecha. Con `count()` cuento cuántos hay vigentes, y eso me devuelve un `Mono<Long>`.

### Qué se ve en la consola
Cuando corro el proyecto, después del arranque de Spring aparecen 6 pruebas:

0. **Sin suscripción:** armo el flujo pero no hago `subscribe()`, y no sale nada.
1. **Eventos vigentes:** llegan 4 por `onNext` y al final `onComplete`. El inactivo no aparece.
2. **Conteo y búsqueda:** el conteo da 4. Al buscar el id 2 lo encuentra, y con el id 999 el `Mono` termina vacío porque no existe.
3. **Sin registros activos:** filtré por el lugar "Gimnasio", que solo tiene el evento inactivo. No hay ningún `onNext`, llega `onComplete` y el conteo da 0.
4. **Error:** metí un evento con la fecha mal escrita ("pronto"). Salen los 4 `onNext` y luego `onError`. Ahí no llega `onComplete`.
5. **Demanda y cancelación:** con un `BaseSubscriber` pido un elemento a la vez con `request(1)`. Recibo 2 y llamo a `cancel()`. Después de eso ya no llega nada más.

### Lo que aprendí
- **Por qué hay que suscribirse:** un `Flux` o un `Mono` solo describe qué hacer con los datos, pero no los emite hasta que alguien llama a `subscribe()`. En la prueba 0 se ve que, sin eso, no pasa nada.
- **Las señales:** `onNext` entrega cada dato, `onComplete` avisa que todo terminó bien y `onError` avisa que algo falló. Un mismo flujo termina con una de las dos, nunca con las dos.
- **Control de demanda:** con `request(1)` el suscriptor decide cuántos elementos recibe, y con `cancel()` corta el flujo y el productor deja de enviar.

### Cómo probarlo
1. Encender MySQL.
2. Ejecutar `GestorEventosApplication`.
3. Buscar en la consola las pruebas que empiezan con `===== 0.`
4. Para comprobar que la API REST sigue bien, abrir `http://localhost:8080/api/eventos`.

### Pruebas
Las pruebas se realizaron usando la colección de Postman (`Semana14.postman_collection.json`), validando los códigos de estado del 01 al 09.

### Tecnologías
- Spring Boot 3.2.0, Thymeleaf, Spring Data JPA, MySQL
Navegador → EventoController → EventoService → EventoRepository → MySQL
↑
LogAspect (AOP)