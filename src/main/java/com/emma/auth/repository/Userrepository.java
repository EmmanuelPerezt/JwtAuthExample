package com.emma.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.emma.auth.model.Usermodel;

public interface Userrepository extends MongoRepository <Usermodel, String> {
    Usermodel findByUsername(String username);
    
}
