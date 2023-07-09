package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.stereotype.Repository;

import com.cms.model.Course;


// @Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    Course findTopByOrderByCourseIdDesc();

}
