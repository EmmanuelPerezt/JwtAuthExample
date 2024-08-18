package com.emma.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emma.auth.model.Usermodel;
import com.emma.auth.repository.Userrepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
    
    @Autowired
    private Userservice userservice;

    @Autowired
    private Userrepository userrepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String register (Usermodel user){

        userservice.saveUser(user);
        return generateToken(user);
    }


    public String login(String username, String password, String token) throws Exception{
        Usermodel user= userrepository.findByUsername(username);
        

        if(passwordEncoder.matches(password, user.getPassword())){
            if(token != null){
                try{
                    validatetToken(token);
                    return token;
                }catch(Exception e){
                    return "token no valido";
                }
            }
        return generateToken(user);
        }else{
            throw new Exception("Invalid username or password");
        }
    
    }   


    public String generateToken(Usermodel user){
         Map<String, Object> claims = new HashMap<>();
                return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token válido por 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public Claims validatetToken(String token){


        return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

}
