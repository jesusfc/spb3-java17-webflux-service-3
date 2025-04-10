package com.jesus.webflux.controller;

import com.jesus.webflux.model.Product;
import com.jesus.webflux.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
@SpringBootTest
class ReactiveControllerTest {

    /**
     * Test class for the ReactiveController.
     * This class uses Mockito to mock dependencies and StepVerifier to test reactive streams.
     */
    private ReactiveController reactiveController;

    @Autowired
    private ProductService productService;

    /**
     * Sets up the test environment before each test.
     * A mock of ProductService is created and injected into the ReactiveController.
     */
    @BeforeEach
    void setUp() {
        //ProductService productService = Mockito.mock(ProductService.class);
        reactiveController = new ReactiveController(productService);
    }


    @Test
    void getProductReturnsProductWhenIdExists() {
        Product mockProduct = new Product(100, "Product 1", "Category 1", 100.0, 4);
        Mockito.when(productService.getProductById(100)).thenReturn(Mono.just(mockProduct));

        StepVerifier.create(reactiveController.getProduct(100).getBody())
                .expectNext(mockProduct)
                .expectComplete()
                .verify();
    }


    /**
     * Tests the getFluxNames method of ReactiveController.
     * Verifies that the method emits the expected sequence of names and completes successfully.
     */
    @Test
    void getFluxNames() {
        StepVerifier.create(reactiveController.getFluxNames())
                .expectNext("Jesús") // Verifies the first emitted value is "Jesús".
                .expectNext("María") // Verifies the second emitted value is "María".
                .expectNextCount(3)  // Verifies that three more values are emitted.
                .expectComplete()    // Verifies that the stream completes successfully.
                .verify();
    }

    // Test for the getFlux method of ReactiveController.
    // Verifies that the method emits a number between 1 and 10 and completes successfully.
    @Test
    void getFlux() {
        StepVerifier.create(reactiveController.getFlux())
                .expectNextMatches(number -> number >= 1 && number <= 10) // Verifies the emitted number is between 1 and 10.
                .expectNextCount(9) // Verifies that ten more values are emitted.
                .expectComplete() // Verifies that the stream completes successfully.
                .verify();
    }


}