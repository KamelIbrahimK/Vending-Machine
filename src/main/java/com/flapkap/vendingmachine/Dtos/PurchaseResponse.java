package com.flapkap.vendingmachine.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {
    private Double totalCost;
    private String productName;
    private Integer amount;
    private Double change;

}
