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

