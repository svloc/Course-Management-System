package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cms.model.Course;

public interface CourseRepository extends MongoRepository<Course, String> {

    Course findTopByOrderByCourseIdDesc();

}
