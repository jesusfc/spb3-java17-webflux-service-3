package com.jesus.webflux.controller;

import com.jesus.webflux.model.Product;
import com.jesus.webflux.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on mar - 2025
 */

/**
 * Para que PostMan en las peticiones actue como un cliente reactivo debemos
 * añadir en el header "Accept: text/event-stream"
 */
@AllArgsConstructor
@RestController
@RequestMapping("/reactive")
public class ReactiveController {

    private final ProductService productService;

    /**
     * Maneja solicitudes HTTP GET para obtener un producto por su ID.
     *
     * @param id El ID del producto a buscar.
     * @return Un `Mono<Product>` que emite el producto encontrado después de un retraso de 2 segundos.
     */
    @GetMapping("/product/{id}")
    public Mono<Product> getProduct(@PathVariable long id) {
        return productService.getProductById(id)
                .delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
    }


    /**
     * Maneja solicitudes HTTP GET para obtener todos los productos.
     *
     * @return Un `Flux<Product>` que emite todos los productos encontrados,
     * cada uno después de un retraso de 2 segundos.
     */
    @GetMapping("/product/all")
    public Flux<Product> getProducts() {
        return productService.getAllProducts()
                .delayElements(Duration.ofSeconds(2)); // Simula una operación asíncrona
    }

    @GetMapping("/mono/{id}")
    public Mono<String> getMono(@PathVariable Long id) {
        return Mono.just("Resultado Mono para ID: " + id)
                .delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
    }

    @GetMapping("/names")
    public Flux<String> getFluxNames() {
        List<String> names = List.of("Jesús", "María", "José", "Pedro", "Pablo");
        return Flux.fromIterable(names)
                .delayElements(Duration.ofSeconds(2)); // Simula una operación asíncrona
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
