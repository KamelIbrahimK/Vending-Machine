package com.flapkap.vendingmachine.entities;

import com.flapkap.vendingmachine.enums.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String password;
    private Double deposit;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public User(String userName, String password, Double deposit, RoleEnum role) {
        this.userName = userName;
        this.password = password;
        this.deposit = deposit;
        this.role = role;
    }
}
