package com.jerzykwiatkowski.warehouse.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String model;
    private String category;
    private int quantity;
    private int inStock;

    public Item() {
    }

    public Item(String name, String type, String model, String category, int quantity, int inStock) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.category = category;
        this.quantity = quantity;
        this.inStock = inStock;
    }
}
