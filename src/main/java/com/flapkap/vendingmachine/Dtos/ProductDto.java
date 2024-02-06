package com.flapkap.vendingmachine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer amountAvailable;
    private Double cost;
    private String productName;
    private Integer sellerId;
}
