package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cms.model.Associate;

public interface AssociateRepository extends MongoRepository<Associate, String> {

}
