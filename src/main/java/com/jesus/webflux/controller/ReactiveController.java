package com.jesus.webflux.controller;

import com.jesus.webflux.model.Product;
import com.jesus.webflux.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value ="/product/{id}")
    public ResponseEntity<Mono<Product>> getProduct(@PathVariable long id) {
        Mono<Product> productById = productService.getProductById(id).delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
        return new ResponseEntity<>(productById, HttpStatus.OK); // Simula una operación asíncrona
    }


    /**
     * Maneja solicitudes HTTP GET para obtener todos los productos.
     *
     * @return Un `Flux<Product>` que emite todos los productos encontrados,
     * cada uno después de un retraso de 2 segundos.
     */
    @GetMapping("/product/all")
    public ResponseEntity<Flux<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK); // Simula una operación asíncrona
    }


    /**
     * Handles HTTP GET requests to retrieve products by category.
     *
     * @param category The category of the products to retrieve.
     * @return A `Flux<Product>` that emits the products found in the specified category,
     * each after a delay of 2 seconds.
     */
    @GetMapping("/product/category/{category}")
    public Flux<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category).delayElements(Duration.ofSeconds(2)); // Simulates an asynchronous operation
    }

    /**
     * Handles HTTP GET requests to retrieve products by name.
     *
     * @param name The name of the products to retrieve.
     * @return A `Flux<Product>` that emits the products found with the specified name,
     * each after a delay of 2 seconds.
     */
    @GetMapping("/product/name/{name}")
    public Flux<Product> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name).delayElements(Duration.ofSeconds(2)); // Simulates an asynchronous operation
    }


    /**
     * Handles HTTP GET requests to retrieve products by name and category.
     *
     * @param name     The name of the products to retrieve.
     * @param category The category of the products to retrieve.
     * @return A `Flux<Product>` that emits the products found with the specified name and category,
     * each after a delay of 2 seconds.
     */
    @GetMapping("/product/name/{name}/category/{category}")
    public Flux<Product> getProductsByNameAndCategory(@PathVariable String name, @PathVariable String category) {
        return productService.getProductsByNameAndCategory(name, category).delayElements(Duration.ofSeconds(2)); // Simulates an asynchronous operation
    }

    @GetMapping("/mono/{id}")
    public ResponseEntity<Mono<String>> getMono(@PathVariable long id) {
        return new ResponseEntity<>(Mono.just("Resultado Mono para ID: " + id), HttpStatus.OK); // Simula una operación asíncrona
    }

    @GetMapping("/names")
    public Flux<String> getFluxNames() {
        List<String> names = List.of("Jesús", "María", "José", "Pedro", "Pablo");
        return Flux.fromIterable(names).delayElements(Duration.ofSeconds(2)); // Simula una operación asíncrona
    }

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        return Flux.range(1, 10).delayElements(Duration.ofMillis(500)); // Simula una operación asíncrona
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
