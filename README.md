# Gestor de Eventos

Este es mi proyecto del semestre para Programación IV. Es una aplicación web para administrar eventos (crear, ver, editar, buscar y eliminar), hecha con **Spring Boot**. Fui construyéndola semana a semana: empecé con una página sencilla y poco a poco le fui agregando base de datos, usuarios, una API REST y, en la última semana, una parte de programación reactiva.

En este README explico qué es el proyecto, cómo se ejecuta y qué hice en cada semana.

---

## Con qué está hecho

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring MVC** y **Thymeleaf** para las páginas web
- **Spring Data JPA** y **MySQL** para guardar los datos
- **AOP** (programación orientada a aspectos) para el registro de actividad en consola
- **Project Reactor** (`reactor-core`) para la parte reactiva
- **Maven** para manejar las dependencias

## Cómo está organizado

Separé el código por capas para que cada clase tenga una sola responsabilidad:

```text
src/main/java/edu/instituto/gestor_eventos/
├── GestorEventosApplication.java   (clase principal)
├── aspect/        LogAspect
├── config/        WebConfig
├── controller/    controladores de las páginas y de la API
├── dto/           EventoRequest, EventoResponse
├── exception/     excepciones propias
├── interceptor/   LoginInterceptor
├── model/         Evento, Categoria, Usuario, EventoForm
├── reactivo/      EventoReactivoService, EventoReactivoRunner
├── repository/    acceso a la base de datos
└── service/       lógica del negocio
```

El recorrido normal de una petición es este:

```text
Navegador → Controller → Service → Repository → MySQL
                 ↑
        LogAspect (AOP) y LoginInterceptor
```

## Cómo ejecutarlo

1. Encender MySQL (la conexión se configura en `application.properties`).
2. Ejecutar la aplicación desde IntelliJ (`GestorEventosApplication`) o con este comando:

```bash
./mvnw spring-boot:run
```

3. Abrir en el navegador:
   - Registro de usuarios: http://localhost:8080/registro
   - Lista de eventos: http://localhost:8080/eventos
   - API REST: http://localhost:8080/api/eventos

---

## Mi avance semana por semana

### Semana 1 – Configuración inicial
Creé el proyecto con Spring Initializr y agregué las dependencias Spring Web y Thymeleaf. Dejé lista la estructura base y el archivo `application.properties`.

### Semana 2 – Fundamentos de Spring
Aprendí a organizar el proyecto por capas (controller, service y model) y creé los paquetes correspondientes. Hice la clase `Evento` como POJO y practiqué las anotaciones básicas: `@SpringBootApplication`, `@Service` y `@RestController`.

### Semana 3 – Configuración y dependencias
Configuré las dependencias de la aplicación web, implementé los primeros repositorios y ajusté el servidor desde `application.properties`.

### Semana 4 – Spring MVC y Thymeleaf
Hice mi primer controlador MVC con `@Controller` y `@GetMapping`, y una vista dinámica con Thymeleaf (`th:each`, `th:text`). Ya quedó funcionando la ruta `/eventos` y entendí cómo se separan las responsabilidades: Controller → Service → Model → Vista.

### Semana 5 – Programación orientada a aspectos (AOP)
Agregué la dependencia `aspectjweaver` y creé el paquete `aspect` con la clase `LogAspect` (`@Aspect` y `@Component`). Con un pointcut sobre la capa de servicios y las anotaciones `@Before` y `@After`, la aplicación imprime en consola un registro cada vez que se usa un método del servicio, por ejemplo al entrar a `/eventos`.

### Semana 7 – Formularios y validación
Creé la clase `EventoForm` con validaciones `@NotBlank` y el formulario `nuevo-evento.html`. Hice las rutas GET y POST de `/eventos/nuevo`, validé los datos con `@Valid` y `BindingResult`, y muestro mensajes de error cuando falta algún campo. Si todo está bien, el registro redirige a `/eventos`.

### Semana 8 – Persistencia con JPA y MySQL
Agregué Spring Data JPA y el conector de MySQL. Convertí `Evento` en entidad (`@Entity`, `@Id`, `@GeneratedValue`), creé `EventoRepository` con `JpaRepository` y configuré la conexión en `application.properties` con `ddl-auto=update`. A partir de aquí los eventos se guardan de verdad en la base de datos: Formulario → Controlador → Servicio → Repositorio → MySQL.

### Semana 9 – CRUD completo
Completé las cuatro operaciones sobre los eventos:
- **Crear:** formulario `nuevo-evento.html` con validaciones.
- **Listar:** los eventos salen desde MySQL en `eventos.html`.
- **Editar:** `editar-evento.html` con los datos ya cargados (`/eventos/editar/{id}`).
- **Eliminar:** botón directo desde el listado (`/eventos/eliminar/{id}`).

### Semana 10 – Validaciones, búsqueda, paginación y relaciones
Esta semana mejoré bastante la aplicación:
1. **Validaciones:** usé `@NotBlank`, `@NotNull` y otras en las entidades, junto con `@Valid` y `BindingResult`. Los errores salen en rojo en los formularios de crear y editar.
2. **Búsqueda:** se puede buscar por nombre con una consulta derivada (`findByNombreContainingIgnoreCase`), sin importar mayúsculas ni minúsculas, y hay un botón para limpiar la búsqueda.
3. **Paginación:** muestro 6 tarjetas por página con `Pageable` y `PageRequest`, con botones de Anterior, Siguiente y números de página. También controlo el caso en que una búsqueda no encuentra nada.
4. **Relación entre entidades:** relacioné `Evento` con `Categoria` usando `@ManyToOne`, y agregué un selector de categoría en los formularios.
5. **Mensajes al usuario:** usé `RedirectAttributes` para mostrar mensajes de éxito al guardar o eliminar, el patrón Post-Redirect-Get para que un formulario no se reenvíe por error, y una ventana de confirmación antes de eliminar.

### Semana 11 – Sesiones, cookies y manejo de errores
- **Inicio y cierre de sesión** con `HttpSession`.
- **Registro de usuarios** con validación.
- **Roles:** el ADMIN puede crear, editar y eliminar; el USER solo puede ver.
- **Interceptor** (`LoginInterceptor`) que protege las rutas `/eventos` y `/preferencia`.
- **Cookie de preferencia** para el tema claro u oscuro, que dura 30 días.
- **Modo oscuro** guardado en `localStorage`.
- **Páginas de error personalizadas** para 404, 400 y 500.

### Semana 13 – API CRUD de eventos
Convertí la gestión de eventos en un servicio web. Con Postman se pueden consultar, crear, modificar y eliminar eventos, y la API responde en JSON. Usa el mismo `EventoService` y la misma base de datos del resto del proyecto.

| Método | Ruta | Qué hace | Códigos |
|---|---|---|---|
| GET | `/api/eventos` | Lista todos los eventos | 200 |
| GET | `/api/eventos/{id}` | Busca un evento | 200, 404 |
| POST | `/api/eventos` | Crea un evento | 200, 400 |
| PUT | `/api/eventos/{id}` | Modifica un evento | 200, 400, 404 |
| DELETE | `/api/eventos/{id}` | Elimina un evento | 200, 404 |

Los códigos significan: 200, todo salió bien; 400, mandé un dato inválido; 404, el evento no existe.

**Ejemplo de POST `/api/eventos`:**

```json
{
  "nombre": "Feria de Tecnología",
  "descripcion": "Exposición de proyectos de software",
  "fecha": "2026-12-05",
  "lugar": "Auditorio Central",
  "categoria": { "id": 1 }
}
```

La respuesta (200) es el evento creado, con su `id`.

**Ejemplo de error 404** (pedir un evento que ya no existe):

```json
{ "error": "No existe un evento con id 32" }
```

**Ejemplo de error 400** (enviar campos vacíos):

```json
{ "error": "Datos inválidos", "detalles": { "nombre": "El nombre es obligatorio" } }
```

**Evidencias:** las capturas de Postman están en la carpeta `evidencias/` (GET, POST, GET por id, PUT, DELETE, el error 404 y dos errores 400). La colección es `Semana13.postman_collection.json`.

### Semana 14 – Estándares REST en la API
Mejoré la API de la semana 13 aplicando buenas prácticas REST, y ordené mejor el código y las respuestas.

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/eventos` | Lista completa (200 OK) |
| GET | `/api/eventos/{id}` | 200 OK, o 404 si no existe |
| POST | `/api/eventos` | Crea el evento y devuelve la cabecera `Location` (201 Created) |
| PUT | `/api/eventos/{id}` | Modifica el evento (200 OK) |
| DELETE | `/api/eventos/{id}` | Elimina el evento (204 No Content) |

Lo que cambié:
- Separé el código en **Controller** (las rutas) y **Service** (la lógica y las validaciones).
- Usé **DTOs**: `EventoRequest` para lo que envía el cliente y `EventoResponse` para lo que devuelve la API.
- Agregué validaciones en los DTOs para que los campos obligatorios y las fechas cumplan el formato `AAAA-MM-DD`.
- Hice un `@RestControllerAdvice` que captura los errores y responde en JSON con el código HTTP adecuado (400, 404 o 500).

**Pruebas:** usé la colección de Postman `Semana14.postman_collection.json`, y validé los códigos de estado del 01 al 09.

---

## Semana 15 – Programación reactiva con Spring (Flux y Mono)

### Qué hice
Esta semana le agregué al proyecto una parte reactiva que funciona solo desde la consola. No creé endpoints nuevos ni usé WebFlux, y la API REST de las semanas anteriores quedó igual y sigue funcionando.

Para lograrlo agregué la dependencia `reactor-core` al `pom.xml` y creé dos clases en el paquete `reactivo`:

- **`EventoReactivoService`**: es un servicio con `@Service`. Aquí armé los flujos con `Flux` y `Mono`.
- **`EventoReactivoRunner`**: se ejecuta solo cuando arranca la aplicación (usa `CommandLineRunner`) y va mostrando en la consola cada prueba.

### Los datos que usé
Hice 5 eventos de ejemplo que **no se guardan en la base de datos**. Decidí que un evento está activo (vigente) cuando su fecha es de hoy o de un día futuro. El "Torneo de robotica" ya pasó, así que ese es mi registro inactivo.

### Cómo usé los operadores
- `filter()` deja solo los eventos vigentes.
- `map()` convierte cada evento en una frase con el nombre, la categoría, el lugar y la fecha.
- `count()` cuenta cuántos eventos hay vigentes y devuelve un `Mono<Long>`.

### Qué se ve en la consola
Cuando ejecuto el proyecto, después del arranque de Spring aparecen 6 pruebas:

| # | Prueba | Qué pasa |
|---|---|---|
| 0 | Sin suscripción | Armo el flujo pero no hago `subscribe()`, y no sale nada. |
| 1 | Eventos vigentes | Llegan 4 eventos por `onNext` y al final `onComplete`. El inactivo no aparece. |
| 2 | Conteo y búsqueda | El conteo da 4. El id 2 se encuentra, y con el id 999 el `Mono` termina vacío porque no existe. |
| 3 | Sin registros activos | Filtro por el lugar "Gimnasio", que solo tiene el evento inactivo. No hay `onNext`, llega `onComplete` y el conteo da 0. |
| 4 | Error | Agrego un evento con la fecha mal escrita ("pronto"). Salen los 4 `onNext` y luego `onError`, sin `onComplete`. |
| 5 | Demanda y cancelación | Con un `BaseSubscriber` pido un elemento a la vez con `request(1)`. Recibo 2 y llamo a `cancel()`. Después ya no llega nada más. |

### Lo que aprendí
- **Por qué hay que suscribirse:** un `Flux` o un `Mono` solo describe qué hacer con los datos, pero no los emite hasta que alguien llama a `subscribe()`. En la prueba 0 se ve que, sin eso, no pasa nada.
- **Las señales:** `onNext` entrega cada dato, `onComplete` avisa que todo terminó bien y `onError` avisa que algo falló. Un mismo flujo termina con una de las dos, nunca con las dos.
- **Control de demanda:** con `request(1)` el suscriptor decide cuántos elementos recibe, y con `cancel()` corta el flujo y el productor deja de enviar.

### Cómo probarlo
1. Encender MySQL.
2. Ejecutar `GestorEventosApplication`.
3. Buscar en la consola las pruebas que empiezan con `===== 0.`
4. Para comprobar que la API REST sigue bien, abrir http://localhost:8080/api/eventos
