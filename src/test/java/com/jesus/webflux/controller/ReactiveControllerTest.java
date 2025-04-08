package com.jesus.webflux.controller;

import com.jesus.webflux.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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

    /**
     * Sets up the test environment before each test.
     * A mock of ProductService is created and injected into the ReactiveController.
     */
    @BeforeEach
    void setUp() {
        ProductService productService = Mockito.mock(ProductService.class);
        reactiveController = new ReactiveController(productService);
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

}