# Gestor de Eventos - Programacion IV

Proyecto Spring Boot: CRUD web (Thymeleaf), API REST, programacion reactiva con Reactor y modulo Spring WebFlux (Semana 16).

## Ejecucion
1. Requisitos: Java 21, Maven y MySQL en 127.0.0.1:3306.
2. Crear la base: `CREATE DATABASE gestoreventos_db;`
3. Configurar usuario y clave en `src/main/resources/application.properties`.
4. Ejecutar: `.\mvnw.cmd spring-boot:run`
5. La app queda en http://localhost:8080

## Endpoints WebFlux (Semana 16)
| Metodo | Ruta | Tipo | Descripcion |
|---|---|---|---|
| GET | /api/v1/reactive/bienvenida/{usuario} | Mono<String> | Saluda al usuario e indica cuantos eventos hay registrados |
| GET | /api/v1/reactive/eventos | Flux<String> + SSE | Emite los 5 eventos mas recientes, uno por segundo |
| GET | /api/v1/reactive/estado-api | Mono<String> + WebClient | Consulta /api/eventos de la propia API REST |
| GET | /api/v1/reactive/estado-api-caida | Mono<String> + WebClient | Consulta una fuente caida (puerto 9999) y responde de forma controlada |

Se conservan el CRUD web (/eventos), la API REST (/api/eventos) y la demo reactiva por consola.

## Semana 16 - Spring WebFlux (Mono, Flux, SSE y WebClient)

### Organizacion del modulo
- `webflux/config/WebClientConfig`: configura el WebClient con la URL base (`demo.api.url`).
- `webflux/controller/ReactiveEventoController`: define los endpoints reactivos.
- `webflux/service/EventoReactiveService`: Mono y Flux sobre los eventos guardados en la base de datos.
- `webflux/service/ApiEventosClienteService`: consume HTTP con WebClient (`retrieve()`, `bodyToMono()`, `timeout()`, `onErrorResume()`).

### Pruebas realizadas
Las capturas de pantalla se entregaron en Teams. Resultados obtenidos:

**1. Mono**: `curl.exe -i http://localhost:8080/api/v1/reactive/bienvenida/Andrey`

    HTTP/1.1 200
    Bienvenido a Gestor de Eventos, Andrey. Hay 20 eventos registrados.

**2. Streaming SSE** (llegan uno por uno): `curl.exe -N http://localhost:8080/api/v1/reactive/eventos`

    data:Evento: Festival Electronico | 2027-01-21 | Centro de Eventos | Musica y Conciertos
    data:Evento: Feria de Tecnologia | 2026-12-05 | Auditorio Central | Musica y Conciertos
    data:Evento: Feria de Tecnologia | 2026-12-05 | Auditorio Central | Musica y Conciertos
    data:Evento: Hackaton de Innovacion | 2026-11-28 | Facultad de Ingenieria | Tecnologia
    data:Evento: Clase de Ciclomontanismo | 2026-11-27 | Punto de partida Zona Norte | Deportes

**3. WebClient (fuente disponible)**: `curl.exe -i http://localhost:8080/api/v1/reactive/estado-api`

    HTTP/1.1 200
    WebClient consulto /api/eventos y recibio 20 eventos.

**4. WebClient (fuente no disponible)**: `curl.exe -i http://localhost:8080/api/v1/reactive/estado-api-caida`

    HTTP/1.1 200
    Fuente no disponible (Fuente externa). Causa: WebClientRequestException

`timeout()` y `onErrorResume()` devuelven un mensaje controlado en lugar de un error 500.
## Evidencias
Mono, SSE con `curl.exe -N` y WebClient (fuente disponible):
(Captura entregada en Teams)

WebClient con fuente no disponible (timeout + onErrorResume):
(Captura entregada en Teams)

## Diferencias entre Spring MVC y WebFlux
| | Spring MVC | WebFlux |
|---|---|---|
| Modelo | Bloqueante, un hilo por peticion | No bloqueante, pocos hilos (event loop) |
| Retorno | T / List<T> | Mono<T> / Flux<T> |
| Streaming | Respuesta completa al final | Datos progresivos (SSE) |
| Backpressure | No | Si, el suscriptor controla la demanda |
| Ideal para | CRUD con JPA, paginas web | Streaming, muchas conexiones, consumo de APIs |

Nota: el proyecto tiene `starter-web` y `starter-webflux`; Spring Boot arranca en modo MVC (Tomcat) y los controladores con Mono/Flux funcionan igual. Como JPA es bloqueante, las consultas se ejecutan con `Schedulers.boundedElastic()`.

## Justificacion de la funcionalidad reactiva
El feed de eventos recientes se entrega progresivamente por SSE, sin esperar a tener toda la lista, lo que sirve para mostrar novedades de la agenda en tiempo real. WebClient consulta la API de eventos sin bloquear y, si la fuente falla, `timeout()` y `onErrorResume()` evitan un error 500.

