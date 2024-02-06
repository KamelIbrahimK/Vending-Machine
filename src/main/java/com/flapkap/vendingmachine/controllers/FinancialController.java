package com.flapkap.vendingmachine.controllers;

import com.flapkap.vendingmachine.dtos.DepositDto;
import com.flapkap.vendingmachine.dtos.PurchaseDto;
import com.flapkap.vendingmachine.dtos.PurchaseResponse;
import com.flapkap.vendingmachine.common.GenericException;
import com.flapkap.vendingmachine.common.GenericResponse;
import com.flapkap.vendingmachine.services.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/Financial")
public class FinancialController {
    @Autowired
    private FinancialService financialService;

    @PostMapping(path = "/deposit")
    public GenericResponse deposite(@RequestBody DepositDto depositDto) throws GenericException {
        return financialService.deposite(depositDto);
    }
    @PostMapping(path = "/buy")
    public PurchaseResponse buy(@RequestBody PurchaseDto purchaseDto) throws GenericException {
        return financialService.buy(purchaseDto);
    }
    @PostMapping(path = "/reset/{buyerId}")
    public GenericResponse reset(@PathVariable("buyerId") Integer buyerId)  {
        return financialService.reset(buyerId);
    }


}
