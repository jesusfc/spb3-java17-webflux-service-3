package com.jesus.webflux.service;

import com.jesus.webflux.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    void getProductByCategoryTest() {
        Flux<Product> productsByCategory = productService.getProductsByCategory("Alimentación");
        StepVerifier.create(productsByCategory)
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Azúcar"))
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Leche"))
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Huevos"))
                .verifyComplete();
    }

    @Test
    void getProductByPriceRangeTest() {
        Flux<Product> productsByPriceRange = productService.getProductsByPriceRange(1.0, 2.0);
        StepVerifier.create(productsByPriceRange)
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Azúcar"))
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Leche"))
                .verifyComplete();
    }

}
