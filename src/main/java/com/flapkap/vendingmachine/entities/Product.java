package com.flapkap.vendingmachine.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private Integer amountAvailable;
    private Double cost;
    private String productName;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    public Product(Integer amountAvailable, Double cost, String productName, User seller) {
        this.amountAvailable = amountAvailable;
        this.cost = cost;
        this.productName = productName;
        this.seller = seller;
    }
}
