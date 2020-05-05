package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "ORDERS")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private Integer orderAmount;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
