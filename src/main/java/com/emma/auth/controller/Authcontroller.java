package com.emma.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emma.auth.model.Usermodel;
import com.emma.auth.service.AuthService;


@RestController
@RequestMapping("/auth")
public class Authcontroller {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Usermodel user){
        String token = authService.register(user);

        return ResponseEntity.status(200).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
        @RequestParam String username,
        @RequestParam String password,
        @RequestHeader(value = "Authorization", required = false) String token
    ){
       
       try{
       
        String autorizertoken = authService.login( username, password, token);

       return ResponseEntity.ok().body("Bearer " + autorizertoken + "\nlogin sucess");
       }catch(Exception e){
        return ResponseEntity.status(401).body("Invalid username or password");      
       }
    }
}
