package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.CourseServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseServiceImpl courseService;
    @Autowired
    AdmissionProxy admissionService;
    @Autowired
    AuthenticationAuthorizationProxy authService;

    @PostMapping("/addCourse")
    public ResponseEntity<Object> addCourse(@RequestBody Course course,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization, "ROLE_ADMIN")) {
            // If the token is not valid, return an unauthorized response with a message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        try {
            Course savedCourse = courseService.addCourse(course);
            return ResponseEntity.ok(savedCourse);
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update/{courseId}/{duration}")
    public ResponseEntity<Object> updateCourse(@PathVariable("courseId") String courseId,
            @PathVariable("duration") Integer duration, @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization, "ROLE_ADMIN")) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Course updatedCourse = courseService.updateCourse(courseId, duration);
            return ResponseEntity.ok(updatedCourse);

        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewByCourseId/{courseId}")
    public ResponseEntity<Object> viewByCourseId(@PathVariable("courseId") String courseId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Course course = courseService.viewByCourseId(courseId);
            return ResponseEntity.ok(course);

        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewFeedbackRating/{courseId}")
    public ResponseEntity<Object> findFeedbackRatingForCourseId(@PathVariable("courseId") String courseId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            float feedback = courseService.findFeedbackRatingForCourseId(courseId);
            return ResponseEntity.ok(feedback);
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/calculateAverageFeedback/{courseId}/{rating}")
    public ResponseEntity<Object> calculateAverageFeedback(
            @PathVariable("courseId") String courseId,
            @PathVariable("rating") float rating, @RequestHeader("Authorization") String authorization) {

        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {

            // Update the course with the calculated average rating
            Course updatedCourse = courseService.calculateAverageFeedbackAndUpdate(courseId, rating);

            return ResponseEntity.ok(updatedCourse);
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deactivateCourse/{courseId}")
    @Retryable
    public ResponseEntity<String> deactivateCourse(@PathVariable("courseId") String courseId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization, "ROLE_ADMIN")) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            admissionService.deactivateAdmission(courseId, authorization);
            Course deactivatedCourse = courseService.deactivateCourse(courseId);
            if (deactivatedCourse != null) {
                String json = "{\"message\":\"" + "Course deactivated successfully." + "\"}";
                return ResponseEntity.ok(json);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CourseInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Course>> viewAll(@RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Course> courseList = courseService.viewAll();
        return ResponseEntity.ok(courseList);
    }

    public String fallback() {
        return "Sorry,Service is unavailable";
    }

}
