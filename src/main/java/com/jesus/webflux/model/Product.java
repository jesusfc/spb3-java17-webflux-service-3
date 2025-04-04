package com.jesus.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on abr - 2025
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private Integer codProduct;
    private String name;
    private String category;
    private double unitPrice;
    private int stock;
}
