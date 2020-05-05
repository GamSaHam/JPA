package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

}
