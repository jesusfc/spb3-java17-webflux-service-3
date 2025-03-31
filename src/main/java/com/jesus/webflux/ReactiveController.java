package com.jesus.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on mar - 2025
 */
@RestController
public class ReactiveController {
    @GetMapping("/mono/{id}")
    public Mono<String> getMono(@PathVariable Long id) {
        return Mono.just("Resultado Mono para ID: " + id)
                .delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
    }

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500)); // Simula una operación asíncrona
    }

    @GetMapping("/stream")
    public Flux<Object> getStream() {
        return Flux.generate(() -> 0, (state, sink) -> {
            sink.next("Elemento " + state);
            if (state == 9) {
                sink.complete();
            }
            return state + 1;
        }).delayElements(Duration.ofMillis(500));
    }

}
