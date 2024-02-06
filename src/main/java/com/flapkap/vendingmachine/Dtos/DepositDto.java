package com.flapkap.vendingmachine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositDto {
    private Integer depositAmount;
    private Integer buyerId;
}
