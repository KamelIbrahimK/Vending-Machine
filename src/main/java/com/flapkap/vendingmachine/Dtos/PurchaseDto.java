package com.flapkap.vendingmachine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseDto {
    private Integer productId;
    private Integer amountOfProduct;
    private Integer buyerId;
}
