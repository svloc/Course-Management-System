package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cms.model.Login;

public interface UserRepository extends MongoRepository<Login, String>{
    Login findByUsername(String username);
}
