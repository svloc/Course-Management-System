package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;

@Service
public class CourseServiceImpl implements ICourseService {
	@Autowired
	CourseRepository courseRepository;

	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException {

		return courseRepository.save(cObj);
	}

	@Override
	public Course updateCourse(String courseId, Integer duration) throws CourseInvalidException {
		return null;
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		return null;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		return null;
	}

	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		return 0;
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {

		return null;

	}

	@Override
	public List<Course> viewAll() {

		return null;
	}

}
