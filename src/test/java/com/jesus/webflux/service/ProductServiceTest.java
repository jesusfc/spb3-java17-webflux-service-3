package com.jesus.webflux.service;

import com.jesus.webflux.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    // Test for getProductById
    @Test
    void getProductByIdTest() {
        StepVerifier.create(productService.getProductById(100))
                .expectNextMatches(p -> p.getCodProduct() == 100 && p.getName().equalsIgnoreCase("Azúcar"))
                .verifyComplete();
    }

    // Test for getAllProducts
    @Test
    void getAllProductsTest() {
        Flux<Product> allProducts = productService.getAllProducts();
        StepVerifier.create(allProducts)
                .expectNextCount(8) // Check that there are 7 products
                .verifyComplete();
    }

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

    @Test
    void getProductByStockTest() {
        Flux<Product> productsByStock = productService.getProductsByStock(0, 6);
        StepVerifier.create(productsByStock)
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Mesa"))
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Fregona"))
                .verifyComplete();
    }

    @Test
    void getProductByNameTest() {
        StepVerifier.create(productService.getProductsByName("Azúcar"))
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Azúcar"))
                .verifyComplete();
    }

    @Test
    void getProductByNameAndCategoryTest() {
        Flux<Product> productsByNameAndCategory = productService.getProductsByNameAndCategory("Azúcar", "Alimentación");
        StepVerifier.create(productsByNameAndCategory)
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("Azúcar"))
                .verifyComplete();
    }

    @Test
    void addProductTest() {
        Product newProduct = new Product(108, "New Product", "New Category", 10.0, 5);
        Mono<Product> productMono = productService.addProduct(newProduct);
        StepVerifier.create(productMono)
                .expectNextMatches(product -> product.getName().equalsIgnoreCase("New Product"))
                .expectComplete()
                .verify();
    }

    @Test
    void updateProductTest() {
        Product updatedProduct = new Product(100, "Updated Product", "Updated Category", 20.0, 10);
        Mono<Product> productMono = productService.updateProduct(updatedProduct);
        StepVerifier.create(productMono)
                .consumeNextWith(product -> {
                    // Check that the product was updated in the list
                    assert productService.getProductById(100).block().getName().equalsIgnoreCase("Updated Product");
                })
                .verifyComplete();
    }

    @Test
    void deleteProductTest() {
        productService.deleteProduct(100);
        StepVerifier.create(productService.getProductById(100))
                .expectNextCount(0) // Check that the product was deleted
                .verifyComplete();
    }

}
