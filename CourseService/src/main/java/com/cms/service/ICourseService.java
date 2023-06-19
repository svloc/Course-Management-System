package com.cms.service;

import java.util.List;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;

public interface ICourseService {

	public Course addCourse(Course cObj) throws CourseInvalidException;
	public Course updateCourse(String courseId,Integer duration) throws CourseInvalidException;
	public List<Course> viewAll();

	public Course viewByCourseId(String courseId) throws CourseInvalidException;
	public Course calculateAverageFeedbackAndUpdate(String courseId,float rating) throws CourseInvalidException;
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException;
	public Course deactivateCourse(String courseId) throws CourseInvalidException;
}
