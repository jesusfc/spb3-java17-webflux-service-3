package com.jesus.webflux.service;

import com.jesus.webflux.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
@Transactional
@Service
public class ProductServiceImpl implements ProductService {


    private static final List<Product> PRODUCT_LIST = Arrays.asList(
            new Product(100, "Azúcar", "Alimentación", 1.10, 20),
            new Product(101, "Leche", "Alimentación", 1.20, 15),
            new Product(102, "Jabón", "Limpieza", 0.89, 30),
            new Product(103, "Mesa", "Hogar", 125, 4),
            new Product(104, "Televisión", "Hogar", 650, 10),
            new Product(105, "Huevos", "Alimentación", 2.20, 30),
            new Product(106, "Fregona", "Limpieza", 3.40, 6),
            new Product(107, "Detergente", "Limpieza", 8.7, 12));


    @Override
    public Mono<Product> getProductById(long id) {
        return Flux.fromIterable(PRODUCT_LIST).filter(product -> product.getCodProduct() == id)
                .next()
                .switchIfEmpty(Mono.just(new Product()));



        /*
        return Mono.justOrEmpty(PRODUCT_LIST.stream()
                .filter(product -> product.getCodProduct() == id)
                .findFirst());

         */
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        try {
            if (PRODUCT_LIST.stream().anyMatch(p -> p.getCodProduct() == product.getCodProduct())) {
                // Update existing product  on the list
                PRODUCT_LIST.stream()
                        .filter(p -> p.getCodProduct() == product.getCodProduct())
                        .findFirst()
                        .ifPresent(p -> {
                            p.setName(product.getName());
                            p.setCategory(product.getCategory());
                            p.setUnitPrice(product.getUnitPrice());
                            p.setStock(product.getStock());
                        });
            } else {
                // Get the last product id
                int lastId = PRODUCT_LIST.stream()
                        .mapToInt(Product::getCodProduct)
                        .max()
                        .orElse(0);
                // Set the new product id
                product.setCodProduct(lastId + 1);
                PRODUCT_LIST.add(product);
            }
        } catch (Exception e) {
            return Mono.error(e);
        }
        return Mono.just(product);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return Mono.justOrEmpty(PRODUCT_LIST.stream()
                .filter(p -> p.getCodProduct() == product.getCodProduct())
                .findFirst()
                .map(p -> {
                    p.setName(product.getName());
                    p.setCategory(product.getCategory());
                    p.setUnitPrice(product.getUnitPrice());
                    p.setStock(product.getStock());
                    return p;
                }));
    }

    @Override
    public void deleteProduct(int id) {
        PRODUCT_LIST.removeIf(product -> product.getCodProduct() == id);
    }

    @Override
    public Flux<Product> getAllProducts() {
        // return Flux.fromIterable(PRODUCT_LIST);
        return Flux.fromIterable(PRODUCT_LIST).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByCategory(String category) {
        Predicate<Product> predicate = product -> product.getCategory().equalsIgnoreCase(category);
        return Flux.fromIterable(PRODUCT_LIST).filter(predicate).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        Predicate<Product> predicate = product -> product.getUnitPrice() >= minPrice && product.getUnitPrice() <= maxPrice;
        return Flux.fromIterable(PRODUCT_LIST).filter(predicate).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByStock(int minStock, int maxStock) {
        Predicate<Product> predicate = product -> product.getStock() >= minStock && product.getStock() <= maxStock;
        return Flux.fromIterable(PRODUCT_LIST).filter(predicate).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByName(String name) {
        Predicate<Product> predicate = product -> product.getName().equalsIgnoreCase(name);
        return Flux.fromIterable(PRODUCT_LIST).filter(predicate).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByNameAndCategory(String name, String category) {
        Predicate<Product> predicate = product -> product.getName().equalsIgnoreCase(name) && product.getCategory().equalsIgnoreCase(category);
        return Flux.fromIterable(PRODUCT_LIST).filter(predicate).delayElements(java.time.Duration.ofMillis(500));
    }

    @Override
    public Flux<Product> getProductsByNameAndPriceRange(String name, double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public Flux<Product> getProductsByNameAndStock(String name, int minStock, int maxStock) {
        return null;
    }

    @Override
    public Flux<Product> getProductsByCategoryAndPriceRange(String category, double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public Flux<Product> getProductsByCategoryAndStock(String category, int minStock, int maxStock) {
        return null;
    }

    @Override
    public Flux<Product> getProductsByPriceRangeAndStock(double minPrice, double maxPrice, int minStock, int maxStock) {
        return null;
    }

    @Override
    public Flux<Product> getProductsByNameAndCategoryAndPriceRange(String name, String category, double minPrice, double maxPrice) {
        return null;
    }
}
