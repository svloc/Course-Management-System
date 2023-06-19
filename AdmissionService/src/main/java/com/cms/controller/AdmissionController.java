package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.service.AdmissionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
    @Autowired
    AdmissionServiceImpl admissionService;

    // @PostMapping("/register/{associateId}/{courseId}")
    // public ResponseEntity<Admission> registerAssociateForCourse(@PathVariable("associateId") String associateId,
    //         @PathVariable("courseId") String courseId) {
    //     try {
    //         Admission admission = admissionService.registerAssociateForCourse(associateId, courseId);
    //         if (admission != null) {
    //             return ResponseEntity.ok(admission);
    //         } else {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //         }
    //     } catch (AdmissionInvalidException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }
    // }

    @PutMapping("/calculateFees/{associateId}")
    public ResponseEntity<Integer> calculateFees(@PathVariable("associateId") String associateId) {
        try {
            int fees = admissionService.calculateFees(associateId);
            return ResponseEntity.ok(fees);
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/feedback/{regNo}/{feedback}/{feedbackRating}")
    public ResponseEntity<Admission> addFeedback(
            @PathVariable("regNo") Long regNo,
            @PathVariable("feedback") String feedback,
            @PathVariable("feedbackRating") float feedbackRating) {
        try {
            Admission admission = admissionService.addFeedback(regNo, feedback, feedbackRating);
            if (admission != null) {
                return ResponseEntity.ok(admission);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/highestFee/{associateId}")
    public ResponseEntity<List<String>> getCourseWithHighestFee(@PathVariable("associateId") String associateId) {
        try {
            List<String> courses = admissionService.highestFeeForTheRegisteredCourse(associateId);
            return ResponseEntity.ok(courses);
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewFeedbackByCourseId/{courseId}")
    public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable("courseId") String courseId) {
        try {
            List<String> feedbackList = admissionService.viewFeedbackByCourseId(courseId);
            return ResponseEntity.ok(feedbackList);
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<Boolean> deactivateAdmission(@PathVariable("courseId") String courseId) {
        try {
            boolean deactivated = admissionService.deactivateAdmission(courseId);
            return ResponseEntity.ok(deactivated);
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // @PostMapping("/makePayment/{registrationId}/{fees}")
    // public ResponseEntity<Boolean> makePayment(
    //         @PathVariable("registrationId") int registrationId,
    //         @PathVariable("fees") int fees) {
    //     try {
    //         boolean paymentMade = admissionService.makePayment(registrationId, fees);
    //         return ResponseEntity.ok(paymentMade);
    //     } catch (AdmissionInvalidException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND
    //     }

    // }

}