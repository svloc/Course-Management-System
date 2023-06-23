package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.stereotype.Repository;

import com.cms.model.Admission;

// @Repository
public interface AdmissionRepository extends MongoRepository <Admission,Long>{
}
