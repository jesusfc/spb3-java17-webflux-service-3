package com.jesus.webflux.service;

import com.jesus.webflux.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
public interface ProductService {

    Mono<Product> getProductById(long id);

    Mono<Product> addProduct(Product product);

    Mono<Product> updateProduct(Product product);

    void deleteProduct(int id);

    Flux<Product> getAllProducts();

    Flux<Product> getProductsByCategory(String category);

    Flux<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    Flux<Product> getProductsByStock(int minStock, int maxStock);

    Flux<Product> getProductsByName(String name);

    Flux<Product> getProductsByNameAndCategory(String name, String category);

    Flux<Product> getProductsByNameAndPriceRange(String name, double minPrice, double maxPrice);

    Flux<Product> getProductsByNameAndStock(String name, int minStock, int maxStock);

    Flux<Product> getProductsByCategoryAndPriceRange(String category, double minPrice, double maxPrice);

    Flux<Product> getProductsByCategoryAndStock(String category, int minStock, int maxStock);

    Flux<Product> getProductsByPriceRangeAndStock(double minPrice, double maxPrice, int minStock, int maxStock);

    Flux<Product> getProductsByNameAndCategoryAndPriceRange(String name, String category, double minPrice, double maxPrice);


}
