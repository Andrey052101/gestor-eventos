package edu.instituto.gestor_eventos.reactivo;

import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;

@Component
public class EventoReactivoRunner implements CommandLineRunner {

    private final EventoReactivoService service;

    public EventoReactivoRunner(EventoReactivoService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {

        System.out.println("\n===== 0. SIN SUSCRIPCION: no pasa nada =====");
        service.procesarVigentes()
               .doOnNext(s -> System.out.println("(NO deberia imprimirse) " + s));
        System.out.println("Pipeline construido, pero sin subscribe() no se ejecuto nada.");

        System.out.println("\n===== 1. EVENTOS VIGENTES (filter + map) =====");
        service.procesarVigentes().subscribe(
            s   -> System.out.println("onNext     -> " + s),
            err -> System.out.println("onError    -> " + err.getMessage()),
            ()  -> System.out.println("onComplete -> flujo finalizado correctamente")
        );

        System.out.println("\n===== 2. CONTEO Y BUSQUEDA CON MONO =====");
        service.contarVigentes()
               .subscribe(n -> System.out.println("Eventos vigentes: " + n));
        service.buscarPorId(2)
               .subscribe(e -> System.out.println("Encontrado: " + e.nombre()));
        service.buscarPorId(999).subscribe(
            e   -> System.out.println("Encontrado: " + e.nombre()),
            err -> System.out.println("onError"),
            ()  -> System.out.println("onComplete -> Mono vacio, el id 999 no existe")
        );

        System.out.println("\n===== 3. CASO SIN REGISTROS ACTIVOS =====");
        service.procesarVigentesEnLugar("Gimnasio").subscribe(
            s   -> System.out.println("onNext     -> " + s),
            err -> System.out.println("onError    -> " + err.getMessage()),
            ()  -> System.out.println("onComplete -> termino SIN emitir ningun elemento")
        );
        service.contarVigentesEnLugar("Gimnasio")
               .subscribe(n -> System.out.println("Eventos vigentes en Gimnasio: " + n));

        System.out.println("\n===== 4. ERROR (onError) =====");
        service.procesarConFechaInvalida().subscribe(
            s   -> System.out.println("onNext     -> " + s),
            err -> System.out.println("onError    -> " + err.getMessage()),
            ()  -> System.out.println("onComplete (no se llega aqui)")
        );

        System.out.println("\n===== 5. CONTROL DE DEMANDA Y CANCELACION =====");
        service.procesarVigentes()
            .doOnRequest(n -> System.out.println("   [upstream] demanda recibida: " + n))
            .subscribe(new BaseSubscriber<String>() {
                int recibidos = 0;

                @Override
                protected void hookOnSubscribe(Subscription s) {
                    System.out.println("Suscrito. Pido 1 elemento con request(1)...");
                    request(1);
                }

                @Override
                protected void hookOnNext(String valor) {
                    recibidos++;
                    System.out.println("Recibido #" + recibidos + " -> " + valor);
                    if (recibidos == 2) {
                        System.out.println("Cancelando con cancel()...");
                        cancel();
                    } else {
                        request(1);
                    }
                }

                @Override
                protected void hookOnComplete() {
                    System.out.println("onComplete (no deberia verse tras cancelar)");
                }

                @Override
                protected void hookOnCancel() {
                    System.out.println("hookOnCancel -> cancelado. Total recibidos: "
                        + recibidos + " de 4 vigentes. Los demas NO llegan.");
                }
            });

        System.out.println("\n===== FIN DEMOSTRACION REACTIVA =====");
    }
}
