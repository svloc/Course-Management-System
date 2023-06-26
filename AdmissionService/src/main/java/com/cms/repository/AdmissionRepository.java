package com.cms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.stereotype.Repository;

import com.cms.model.Admission;
// import com.cms.model.Course;

// @Repository
public interface AdmissionRepository extends MongoRepository<Admission, Long> {

    List<Admission> findCoursesByAssociateId(String associateId);

    List<Admission> findByCourseId(String courseId);

    void deleteByCourseId(String courseId);
}
