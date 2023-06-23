package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.service.CourseServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired CourseServiceImpl courseService;

    @PostMapping("/saveCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.addCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateDuration/{courseId}/{duration}")
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") String courseId,@PathVariable("duration") Integer duration) {
        try {
            Course updatedCourse = courseService.updateCourse(courseId, duration);
            if (updatedCourse != null) {
                return ResponseEntity.ok(updatedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewCourseByCourseId/{courseId}")
    public ResponseEntity<Course> viewByCourseId(@PathVariable("courseId") String courseId) {
        try {
            Course course = courseService.viewByCourseId(courseId);
            if (course != null) {
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewFeedback/{courseId}")
    public ResponseEntity<Float> findFeedbackRatingForCourseId(@PathVariable("courseId") String courseId) {
        try {
            float feedback = courseService.findFeedbackRatingForCourseId(courseId);
            if (feedback > 0) {
               return ResponseEntity.ok(feedback);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/calculateAverageFeedback/{courseId}/{rating}")
    public ResponseEntity<String> calculateAverageFeedbackAndUpdate(
            @PathVariable("courseId") String courseId,
            @PathVariable("rating") float rating) {
        try {
            Course updatedCourse = courseService.calculateAverageFeedbackAndUpdate(courseId, rating);
            if (updatedCourse != null) {
                return ResponseEntity.ok("Average feedback calculated and updated successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<String> deactivateCourse(@PathVariable("courseId") String courseId) {
        try {
            Course deactivatedCourse = courseService.deactivateCourse(courseId);
            if (deactivatedCourse != null) {
                return ResponseEntity.ok("Course deactivated successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Course>> viewAll() {
        List<Course> courseList = courseService.viewAll();
        return ResponseEntity.ok(courseList);
    }

}
