package com.flapkap.vendingmachine.services;

import com.flapkap.vendingmachine.dtos.DepositDto;
import com.flapkap.vendingmachine.dtos.PurchaseDto;
import com.flapkap.vendingmachine.dtos.PurchaseResponse;
import com.flapkap.vendingmachine.common.Constants;
import com.flapkap.vendingmachine.common.GenericException;
import com.flapkap.vendingmachine.common.GenericResponse;
import com.flapkap.vendingmachine.entities.Product;
import com.flapkap.vendingmachine.entities.User;
import com.flapkap.vendingmachine.enums.RoleEnum;
import com.flapkap.vendingmachine.repositories.ProductRepository;
import com.flapkap.vendingmachine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FinancialService {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    private List<Integer> coins = Arrays.asList(5, 10, 20, 50, 100);


    public GenericResponse deposite(DepositDto depositDto) throws GenericException {
        User buyer = productService.isUserAuthorized(depositDto.getBuyerId(), RoleEnum.BUYER);
        log.info("buyer is authorized!");
        if (!coins.contains(depositDto.getDepositAmount())) {
            log.error("wrong deposit coins!");
            throw new GenericException(Constants.WRONG_COIN);
        }
        buyer.setDeposit(depositDto.getDepositAmount().doubleValue());
        userRepository.save(buyer);
        log.info("deposit added successfully!");
        return new GenericResponse(Constants.DEPOSITE_ADDED, buyer);
    }

    public PurchaseResponse buy(PurchaseDto purchaseDto) throws GenericException {
        User buyer = productService.isUserAuthorized(purchaseDto.getBuyerId(), RoleEnum.BUYER);
        log.info("buyer is authorized!");
        // 1. check if product exists
        Optional<Product> product = productRepository.findById(purchaseDto.getProductId());
        if (product.isPresent()) {
            //  2. amount of this product is available
            if (product.get().getAmountAvailable() >= purchaseDto.getAmountOfProduct()) {
                // 3. check if his depossite will cover the bill
                Double bill = purchaseDto.getAmountOfProduct() * product.get().getCost();
                if (buyer.getDeposit() >= bill) {
                    buyer.setDeposit((buyer.getDeposit() - bill));
                    product.get().setAmountAvailable((product.get().getAmountAvailable() - purchaseDto.getAmountOfProduct()));
                    userRepository.save(buyer);
                    productRepository.save(product.get());
                    log.info("Purchase done successfully!");

                    return new PurchaseResponse(bill , product.get().getProductName() , purchaseDto.getAmountOfProduct() , buyer.getDeposit() );


                }
                log.error("Buyer Deposite is less than the bill");
                throw new GenericException(Constants.NOT_ENOUGH_DEPOSITE);
            }
            log.error("User requested amount greater than the available");
            throw new GenericException(String.format(Constants.UNAVAILABLE_AMOUNT, product.get().getAmountAvailable()));
        }
        log.error("the requested product not found");
        throw new GenericException(Constants.PRODUCT_NOT_FOUND);

    }
    public GenericResponse reset(Integer buyerId) {
        User buyer = productService.isUserAuthorized(buyerId, RoleEnum.BUYER);
        GenericResponse response=new GenericResponse(String.format(Constants.RESET_DONE , buyer.getDeposit()) , buyer);
        buyer.setDeposit(0.0);
        userRepository.save(buyer);
        return response;
    }

}
