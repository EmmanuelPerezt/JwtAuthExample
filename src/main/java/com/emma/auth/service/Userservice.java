package com.emma.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emma.auth.model.Usermodel;
import com.emma.auth.repository.Userrepository;



@Service
public class Userservice {
    
    @Autowired
    private Userrepository userrepository;


    @Autowired
    private PasswordEncoder passwordEncoder;




    public Usermodel saveUser(Usermodel user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userrepository.save(user);
    }

    public Usermodel findUser(Usermodel user){


        return userrepository.findByUsername(user.getUsername());
    }
}
