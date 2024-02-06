package com.flapkap.vendingmachine.controllers;

import com.flapkap.vendingmachine.dtos.UserDto;
import com.flapkap.vendingmachine.common.GenericException;
import com.flapkap.vendingmachine.common.GenericResponse;
import com.flapkap.vendingmachine.entities.User;
import com.flapkap.vendingmachine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/User")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = "/createUser")
    public GenericResponse createUser(@RequestBody UserDto userDto) throws GenericException {
        return userService.createUser(userDto);
    }

    @PutMapping(path = "/updateUser")
    public GenericResponse updateUser(@RequestBody UserDto userDto) throws GenericException {
        return userService.updateUser(userDto);
    }

    @DeleteMapping(path = "/deleteUser/{userId}")
    public GenericResponse deleteUser(@PathVariable("userId")Integer userId) throws GenericException {
        return userService.deleteUser(userId);
    }

    @GetMapping(path = "/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/getUserById/{userId}")
    public User getUserById(@PathVariable("userId")Integer userId) throws GenericException {
        return userService.getUserById(userId);
    }
}
