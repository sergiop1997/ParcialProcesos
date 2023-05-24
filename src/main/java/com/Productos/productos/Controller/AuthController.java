package com.Productos.productos.Controller;

import com.Productos.productos.Models.User;
import com.Productos.productos.Service.UserService;
import com.Productos.productos.Utils.ApiResponse;
import com.Productos.productos.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;
    Map data = new HashMap<>();

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){

        try{
            data.put("token",userService.login(user));
            apiResponse = new ApiResponse(Constants.USER_LOGIN, data);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse(e.getMessage(), "");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
}
