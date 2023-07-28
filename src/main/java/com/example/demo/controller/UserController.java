package com.example.demo.controller;


import com.example.demo.bean.BeanValidator;
import com.example.demo.bean.ResultDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BeanValidator beanValidator;

    @GetMapping("/free")
    public String free() {
        return "free";
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> allUsers() {
        System.err.println(":::  UserController.allUsers :::");
        ResultDTO<?> responsePacket = null;
        try {
            responsePacket = new ResultDTO<>(userService.getAllUsers(), "Users fetched successfully !!", true);
            return new ResponseEntity<>(responsePacket, HttpStatus.OK);
        } catch (Exception e) {
            responsePacket = new ResultDTO<>(e.getMessage(), false);
            return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User reqData) {
        System.err.println(":::  UserController.createUser :::");
        ResultDTO<?> responsePacket = null;
        try {
            ArrayList<String> errorList = beanValidator.userValidate(reqData);
            if (errorList.size() != 0) {
                ResultDTO<ArrayList<String>> errorPacket = new ResultDTO<>(errorList,
                        "Above fields values must not be empty", false);
                return new ResponseEntity<>(errorPacket, HttpStatus.BAD_REQUEST);
            }
            User isData = userService.isDataExist(reqData);
            if (isData == null) {
                responsePacket = new ResultDTO<>(userService.saveUser(reqData), "User Created Successfully", true);
                return new ResponseEntity<>(responsePacket, HttpStatus.OK);
            } else {
                responsePacket = new ResultDTO<>("Record already exist", false);
                return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responsePacket = new ResultDTO<>(e.getMessage(), false);
            return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('NORMAL_USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        System.err.println(":::  UserController.getUserById :::");
        ResultDTO<?> responsePacket = null;
        try {
            User user = userService.getUserById(id);
            String message = user != null ? "User fetched successfully !!" : "User not found !!";
            responsePacket = new ResultDTO<>(user,message, true);
            return new ResponseEntity<>(responsePacket, HttpStatus.OK);
        } catch (Exception e) {
            responsePacket = new ResultDTO<>(e.getMessage(), false);
            return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User reqData) {
        System.err.println(":::  UserController.updateUser :::");
        ResultDTO<?> responsePacket = null;
        try {
            User isData = userService.isDataExist(reqData);
            if (isData != null) {
                responsePacket = new ResultDTO<>(userService.updateUser(reqData), "User Updated Successfully",
                        true);
                return new ResponseEntity<>(responsePacket, HttpStatus.OK);
            } else {
                responsePacket = new ResultDTO<>("Record not exist", false);
                return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responsePacket = new ResultDTO<>(e.getMessage(), false);
            return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        System.err.println(":::  UserController.deleteUserById :::");
        ResultDTO<?> responsePacket = null;
        try {
            responsePacket = new ResultDTO<>(userService.deleteUser(id), "User deleted successfully !!", true);
            return new ResponseEntity<>(responsePacket, HttpStatus.OK);
        } catch (Exception e) {
            responsePacket = new ResultDTO<>(e.getMessage(), false);
            return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
        }
    }

}