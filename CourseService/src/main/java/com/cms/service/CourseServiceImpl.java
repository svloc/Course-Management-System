
package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException {
		// String courseId = sequenceGeneratorService.generateCourseId();

		// cObj.setCourseId(courseId);
		Course course = courseRepository.findById(cObj.getCourseId()).orElse(null);
		if (course!=null) {
			log.error("CourseId already exists");
			throw new CourseInvalidException("CourseId already exists");
		}
		log.info("The method addCourse has completed successfully");
		return courseRepository.save(cObj);
	}

	@Override
	public Course updateCourse(String courseId, Integer duration) throws CourseInvalidException {

		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			log.error("CourseId does not exists");
			throw new CourseInvalidException("CourseId does not exists");
		} else {
			course.setDuration(duration);
			courseRepository.save(course);
			log.info("The method updateCourse has completed successfully");
			return course;
		}

	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			log.error("CourseId not found");
			throw new CourseInvalidException("CourseId not found");
		}
		log.info("The method viewByCourseId has completed successfully");
		return course;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		if (courseRepository.existsById(courseId)) {
			Course course = courseRepository.findById(courseId).orElse(null);
			course.setRating(rating);
			courseRepository.save(course);
			log.info("The method calculateAverageFeedbackAndUpdate has completed successfully");
			return course;
		} else {
			log.error("CourseId does not exist");
			throw new CourseInvalidException("CourseId does not exist");
		}
	}

	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {

		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			log.error("CourseId does not exist");
			throw new CourseInvalidException("CourseId does not exist");
		} else {
			log.info("The method findFeedbackRatingForCourseId has completed successfully");
			return course.getRating();
		}

	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		if (courseRepository.existsById(courseId)) {
			Course deactivatedCourse = courseRepository.findById(courseId).orElse(null);
			courseRepository.deleteById(courseId);
			log.info("The method deactivateCourse has completed successfully");
			return deactivatedCourse;
		} else {
			log.error("CourseId does not exist");
			throw new CourseInvalidException("CourseId does not exist");
		}
	}

	@Override
	public List<Course> viewAll() {
		log.info("The method viewAll has completed successfully");
		return courseRepository.findAll();
	}

}
