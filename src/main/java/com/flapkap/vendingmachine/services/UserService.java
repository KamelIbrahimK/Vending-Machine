package com.flapkap.vendingmachine.services;

import com.flapkap.vendingmachine.dtos.UserDto;
import com.flapkap.vendingmachine.common.Constants;
import com.flapkap.vendingmachine.common.GenericException;
import com.flapkap.vendingmachine.common.GenericResponse;
import com.flapkap.vendingmachine.entities.User;
import com.flapkap.vendingmachine.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public GenericResponse createUser(UserDto userDto) throws GenericException {
        Optional<User> user = userRepository.findByUserName(userDto.getUserName());
        if (user.isPresent()) {
            log.error("User is already exist , can't create it again");
            throw new GenericException(Constants.DUPLICATE_USER);
        } else {
            User newUser = new User(userDto.getUserName(), userDto.getPassword(),
                    userDto.getDeposit(), userDto.getRole());
            userRepository.save(newUser);
            log.info("User added successfully");
            return new GenericResponse(Constants.USER_ADDED, newUser);
        }

    }

    public GenericResponse updateUser(UserDto userDto) throws GenericException {
        Optional<User> user = userRepository.findByUserName(userDto.getUserName());
        if (user.isPresent()) {
            user.get().setPassword(userDto.getPassword());
            user.get().setDeposit(userDto.getDeposit());
            user.get().setRole(userDto.getRole());
            userRepository.save(user.get());
            log.info("user updated successfully");
            return new GenericResponse(Constants.UPDATED_USER, user.get());
        } else {
            log.error("user not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);

        }
    }

    public GenericResponse deleteUser(Integer userId) throws GenericException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            log.info("user deleted successfully");
            return new GenericResponse(Constants.DELETED_USER, user);
        } else {
            log.error("user not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) throws GenericException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("User not found");
            throw new GenericException(Constants.UN_AVAILABLE_USER);
        }
    }
}
