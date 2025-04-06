package com.jesus.webflux.controller;

import com.jesus.webflux.model.Product;
import com.jesus.webflux.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Mono<Product>> getProduct(@PathVariable long id) {
        Mono<Product> productById = productService.getProductById(id).delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
        return new ResponseEntity<>(productById, HttpStatus.OK); // Simula una operación asíncrona
    }

    // Create a new product on the list
    @PostMapping(value = "/product/add")
    public ResponseEntity<Mono<Product>> addProduct(@RequestBody Product product) {
        Mono<Product> addedProduct = productService.addProduct(product).delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED); // Simula una operación asíncrona
    }

    @PutMapping(value = "/product/update")
    public ResponseEntity<Mono<Product>> updateProduct(@RequestBody Product product) {

        // if product object is product.getCodProduct() null return bad request
        if (product.getCodProduct() == 0) return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST);

        Mono<Product> addedProduct = productService.addProduct(product).delayElement(Duration.ofSeconds(2)); // Simula una operación asíncrona
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED); // Simula una operación asíncrona
    }

    @GetMapping("/product/all")
    public ResponseEntity<Flux<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK); // Simula una operación asíncrona
    }

    @GetMapping("/product/category/{category}")
    public ResponseEntity<Flux<Product>> getProductsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.getProductsByCategory(category).delayElements(Duration.ofSeconds(2)), HttpStatus.OK); // Simulates an asynchronous operation
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<Flux<Product>> getProductsByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductsByName(name).delayElements(Duration.ofSeconds(2)), HttpStatus.OK); // Simulates an asynchronous operation
    }


    @GetMapping("/product/name/{name}/category/{category}")
    public ResponseEntity<Flux<Product>> getProductsByNameAndCategory(@PathVariable String name, @PathVariable String category) {
        return new ResponseEntity<>(productService.getProductsByNameAndCategory(name, category).delayElements(Duration.ofSeconds(2)), HttpStatus.OK); // Simulates an asynchronous operation
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
