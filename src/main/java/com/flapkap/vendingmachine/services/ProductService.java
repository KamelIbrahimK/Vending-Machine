package com.flapkap.vendingmachine.services;

import com.flapkap.vendingmachine.dtos.ProductDto;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public GenericResponse addProduct(ProductDto productDto) throws GenericException {
        User seller=isUserAuthorized(productDto.getSellerId() , RoleEnum.SELLER);
        log.info(Constants.USER_AUTHORIZED);
        Optional<Product>oldProduct=productRepository.findByproductName(productDto.getProductName());
        if (oldProduct.isPresent()){
            log.error("Product is already exist can't create it again");
            throw new GenericException(Constants.DUPLICATE_PRODUCT);
        }else {
            Product newProduct = new Product(productDto.getAmountAvailable() ,productDto.getCost() ,productDto.getProductName() , seller  );
            Product savedProduct=productRepository.save(newProduct);
            log.info("Product added successfully!");
            return new GenericResponse(Constants.PRODUCT_ADDED, savedProduct);

        }

    }
    public User isUserAuthorized(Integer userId , RoleEnum role) throws GenericException{
        User user=checkIfUserExist(userId);
        if(!user.getRole().equals(role)) {
            throw new GenericException(Constants.UNAUTHORIZED_ACCESS);
        }
        return user;
    }
    public User checkIfUserExist(Integer userId) throws GenericException {
        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        throw new GenericException(Constants.UN_AVAILABLE_USER);
    }


    public GenericResponse updateProduct(ProductDto productDto) throws GenericException {
        isUserAuthorized(productDto.getSellerId() , RoleEnum.SELLER);
        log.info(Constants.USER_AUTHORIZED);
        Optional<Product>oldProduct=productRepository.findByproductName(productDto.getProductName());
        if (!oldProduct.isPresent()){
            log.error(Constants.PRODUCT_NOT_FOUND);
            throw new GenericException(Constants.PRODUCT_NOT_FOUND);
        }else {
            if(!oldProduct.get().getSeller().getUserId().equals(productDto.getSellerId())){
                log.error("can't update other seller product");
                throw new GenericException(Constants.CANNOT_ACCESS_PRODUCT);
            }
            oldProduct.get().setAmountAvailable(productDto.getAmountAvailable());
            oldProduct.get().setCost(productDto.getCost());
            productRepository.save(oldProduct.get());
            log.info("product updated successfully!");
            return new GenericResponse(Constants.PRODUCT_UPDATED, oldProduct.get());

        }
    }

    public GenericResponse deleteProduct(Integer productId, Integer sellerId) throws GenericException {
        isUserAuthorized(sellerId , RoleEnum.SELLER);
        log.info(Constants.USER_AUTHORIZED);
        Optional<Product> oldProduct= productRepository.findById(productId);
        if(!oldProduct.isPresent()){
            log.error(Constants.PRODUCT_NOT_FOUND);
            throw new GenericException(Constants.PRODUCT_NOT_FOUND);
        }else{
            if(!oldProduct.get().getSeller().getUserId().equals(sellerId)){
                log.error("can't delete other seller product");
                throw new GenericException(Constants.CANNOT_ACCESS_PRODUCT);
            }
            productRepository.deleteById(productId);
            log.info("product deleted successfully!");
            return new GenericResponse(Constants.PRODUCT_DELETED, oldProduct.get());

        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    public Product getProductById(Integer productId) throws GenericException {
        Optional<Product> oldProduct= productRepository.findById(productId);
        if(!oldProduct.isPresent()){
            log.error(Constants.PRODUCT_NOT_FOUND);
            throw new GenericException(Constants.PRODUCT_NOT_FOUND);
        }
        return oldProduct.get();

    }
}
