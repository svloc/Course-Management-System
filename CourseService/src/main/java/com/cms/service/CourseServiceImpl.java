
package com.cms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

	@Autowired private CourseRepository courseRepository;

	@Autowired private SequenceGeneratorService seqGeneratorService;

	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException {
		Course course;
		try {
			cObj.setCourseId(seqGeneratorService.generateCourseId());
			course = courseRepository.save(cObj);
		} catch (IllegalArgumentException e) {
			throw new CourseInvalidException("CourseId already exist");
		}
		return course;
	}

	@Override
	public Course updateCourse(String courseId, Integer duration) throws CourseInvalidException {
		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			throw new CourseInvalidException("CourseId does not exists");
		}
		course.setDuration(duration);
		courseRepository.save(course);
		return course;

	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			throw new CourseInvalidException("Course Id does not exist");
		}
		return course;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		Course course=courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			throw new CourseInvalidException("CourseId does not exist");
		}
		course.setRating(rating);
		return courseRepository.save(course);
		
	}

	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			throw new CourseInvalidException("CourseId does not exist");
		} else {
			return course.getRating();
		}

	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		if (courseRepository.existsById(courseId)) {
			Course deactivatedCourse = courseRepository.findById(courseId).orElse(null);
			courseRepository.deleteById(courseId);
			return deactivatedCourse;
		} else {
			throw new CourseInvalidException("CourseId does not exist");
		}
	}

	@Override
	public List<Course> viewAll() {
		return courseRepository.findAll();
	}

}
