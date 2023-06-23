package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.model.Login;

@Repository
public interface UserRepository extends MongoRepository<Login, String>{
    Login findByUsername(String username);
}


