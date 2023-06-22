package com.Productos.productos.Controller;

import com.Productos.productos.Models.User;
import com.Productos.productos.Service.UserService;

import com.Productos.productos.Utils.ApiResponse;
import com.Productos.productos.Utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;

    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable Long id){
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping(value = "")
    public ResponseEntity saveUser(@RequestBody User user){
        Boolean userResp = userService.createUser(user);

        if(userResp == true){
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED,"");
            return new ResponseEntity(apiResponse, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
        return new ResponseEntity(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "")
    public ResponseEntity findAllUser(){
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_LIST,userService.allUsers());
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch(Exception e){
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND,"");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND, (userService.delete(id)));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity(apiResponse, HttpStatus.NOT_FOUND);
        }

    }


}
