package com.flapkap.vendingmachine.dtos;

import com.flapkap.vendingmachine.enums.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String password;
    private Double deposit;
    private RoleEnum role;
}
