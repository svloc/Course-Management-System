package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AssociateProxy;
import com.cms.proxy.CourseProxy;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private CourseProxy courseproxy;

	@Autowired
	private AssociateProxy associateproxy;

	@Autowired
	private AdmissionProxy admissionproxy;

	// Course Service methods 

	@GetMapping(value = "/course/viewByCourseId/{courseId}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Course> viewByCourseId(@PathVariable("courseId") String courseId,
			@RequestHeader("Authorization") String authorization) {
		return courseproxy.viewByCourseId(courseId, authorization);
	}

	// Associate Service methods

	@GetMapping(value = "/associate/viewByAssociateId/{associateId}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId,
			@RequestHeader("Authorization") String authorization) {
		return associateproxy.viewByAssociateId(associateId, authorization);
	}

	@PutMapping(value = "/associate/updateAssociate/{associateId}/{associateAddress}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId,
			@PathVariable String associateAddress, @RequestHeader("Authorization") String authorization) {
		return associateproxy.updateAssociate(associateId, associateAddress, authorization);
	}

	// Admission Service methods

	@PostMapping(value = "/admission/register/{associateId}/{courseId}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Admission> registerAssociateForCourse(@PathVariable String associateId,
			@PathVariable String courseId, @RequestHeader("Authorization") String authorization) {
		return admissionproxy.registerAssociateForCourse(associateId, courseId, authorization);
	}

	@PostMapping(value = "/admission/feedback/{regNo}/{feedback}/{feedbackRating}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Admission> addFeedback(@PathVariable long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating, @RequestHeader("Authorization") String authorization) {
		return admissionproxy.addFeedback(regNo, feedback, feedbackRating, authorization);
	}

	@GetMapping(value = "/admission/viewFeedbackByCourseId/{courseId}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable String courseId,
			@RequestHeader("Authorization") String authorization) {
		return admissionproxy.viewFeedbackByCourseId(courseId, authorization);
	}

}