package com.jerzykwiatkowski.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

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

    public void setCategory(String category) {
        this.category = category.toLowerCase();
    }
}
