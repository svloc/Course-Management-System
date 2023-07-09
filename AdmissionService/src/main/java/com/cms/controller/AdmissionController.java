package com.cms.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.core.Queue;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Course;
import com.cms.payment.Order;
import com.cms.payment.PaypalService;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.proxy.CourseProxy;
import com.cms.proxy.ServiceProxy;
import com.cms.model.Associate;
import com.cms.service.AdmissionServiceImpl;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
    @Autowired
    private AdmissionServiceImpl admissionService;

    @Autowired
    private CourseProxy courseProxy;
    @Autowired
    private ServiceProxy associateProxy;
    @Autowired
    AuthenticationAuthorizationProxy authService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Queue queue;
    @Autowired
    PaypalService paypalService;
    @Value("${spring.mail.username}")
    private String sender;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    // 1.registerAssociateForCourse method
    @PostMapping("/register/{associateId}/{courseId}")
    public ResponseEntity<Object> registerAssociateForCourse(@RequestBody Admission admission,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Extract associateId and courseId from the admission object
        String associateId = admission.getAssociateId();
        String courseId = admission.getCourseId();

        try {
            // Retrieve course and associate information from respective proxies
            ResponseEntity<Course> courseResponse = courseProxy.viewByCourseId(courseId, authorization);
            ResponseEntity<Associate> associateResponse = associateProxy.viewByAssociateId(associateId, authorization);

            // Check if both course and associate are found successfully
            boolean isCourseFound = courseResponse.getStatusCode().is2xxSuccessful();
            boolean isAssociateFound = associateResponse.getStatusCode().is2xxSuccessful();

            if (isCourseFound && isAssociateFound) {
                // Register associate for the course using the admission service
                Admission registeredAdmission = admissionService.registerAssociateForCourse(admission);

                if (registeredAdmission != null) {
                    String message = "Registration Details:\n" +
                            "Associate ID: " + associateId + "\n" +
                            "Course ID: " + courseId + "\n" +
                            "Registration Number: " + registeredAdmission.getRegistrationId();

                    // Send registration details to the RabbitMQ queue
                    rabbitTemplate.convertAndSend(queue.getName(), message);

                    // Send registration email to the associate
                    sendRegistrationEmail(associateResponse.getBody().getAssociateEmailId(), associateId, courseId,
                            registeredAdmission.getRegistrationId());

                    return ResponseEntity.ok(registeredAdmission);
                } else {
                    // Return internal server error response if registration fails
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                // Return not found response if either course or associate is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (AdmissionInvalidException e) {
            // Return internal server error response if admission is invalid or any
            // exception occurs
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private void sendRegistrationEmail(String recipientEmail, String associateId, String courseId,
            long registrationNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipientEmail); // Set the recipient email address
        message.setSubject("Registration Confirmation");
        message.setText("Dear Associate,\n\nCongratulations! You have successfully registered for the course.\n\n" +
                "Registration Details:\n" +
                "Associate ID: " + associateId + "\n" +
                "Course ID: " + courseId + "\n" +
                "Registration Number: " + registrationNumber);
        javaMailSender.send(message);
    }

    // 2.calculateFees method working fine
    @PutMapping("/calculateFees/{associateId}")
    public ResponseEntity<Object> calculateFees(@PathVariable("associateId") String associateId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // Retrieve associate information from associate proxies
            ResponseEntity<Associate> associateResponse = associateProxy.viewByAssociateId(associateId, authorization);

            // Check if ssociate is found successfully
            boolean isAssociateFound = associateResponse.getStatusCode().is2xxSuccessful();

            if (isAssociateFound) {
                int fees = admissionService.calculateFees(associateId);
                return ResponseEntity.ok(fees);
            } else {
                // Return not found response if either course or associate is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (AdmissionInvalidException e) {
            // Return internal server error response if admission is invalid or any
            // exception occurs
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/feedback/{regNo},{feedback},{feedbackRating}")
    public ResponseEntity<Object> addFeedback(@PathVariable("regNo") Long regNo,
            @PathVariable("feedback") String feedback, @PathVariable("feedbackRating") float feedbackRating,
            @RequestHeader("Authorization") String authorization) {

        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // Add feedback for the admission
            Admission admission = admissionService.addFeedback(regNo, feedback, feedbackRating);
            if (admission != null) {
                String courseId = admission.getCourseId();
                // Calculate the average feedback rating and update the course
                courseProxy.calculateAverageFeedbackAndUpdate(courseId, feedbackRating, authorization);
                return ResponseEntity.ok(admission);
            } else {
                // Admission not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (AdmissionInvalidException e) {
            // Invalid admission
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/highestFee/{associateId}")
    public ResponseEntity<List<String>> getCourseWithHighestFee(@PathVariable("associateId") String associateId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // Retrieve the courseIds with the highest fee for the given associateId
            List<String> courses = admissionService.highestFeeForTheRegisteredCourse(associateId);
            // Return the courseIds in the response body
            return ResponseEntity.ok(courses);
        } catch (AdmissionInvalidException e) {
            // Invalid admission or no registered courses found for the associate
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewFeedbackByCourseId/{courseId}")
    public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable("courseId") String courseId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // Retrieve the feedback list for the given courseId
            List<String> feedbackList = admissionService.viewFeedbackByCourseId(courseId);

            // Return the feedback list in the response body
            return ResponseEntity.ok(feedbackList);
        } catch (AdmissionInvalidException e) {
            // Invalid admission or no feedback found for the courseId
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<Object> deactivateAdmission(@PathVariable("courseId") String courseId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // Deactivate the admission associated with the given courseId
            boolean deactivated = admissionService.deactivateAdmission(courseId);

            // Return the deactivation status in the response body
            return ResponseEntity.ok(deactivated);
        } catch (AdmissionInvalidException e) {
            // Invalid admission or no admission found for the courseId
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // ye to mera wala hai..
    // @PostMapping("/makePayment/{registrationId}")
    // public ResponseEntity<Boolean> makePayment(@PathVariable("registrationId")
    // int registrationId,@RequestHeader("Authorization") String authorization) {
    // if (!authService.isValidToken(authorization)) {
    // // If the token is not valid, return an unauthorized response
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    // }
    // try {
    // boolean paymentMade = admissionService.makePayment(registrationId);
    // return ResponseEntity.ok(paymentMade);
    // } catch (AdmissionInvalidException e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // }

    @PostMapping(value = "/makePayment/{registrationId},{fees}", produces = "application/json")
    public ResponseEntity<String> makePayment(@RequestHeader("Authorization") String authorization,
            @PathVariable int registrationId, @PathVariable int fees) throws IOException {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        boolean paymentMade = false;

        try {
            paymentMade = admissionService.makePayment(registrationId);
        } catch (AdmissionInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        try {
            if (paymentMade) {
                Order order = new Order(fees, "USD", "paypal", "sale", "Admission Fees");
                Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                        order.getIntent(), order.getDescription(), "http://localhost:4200/" + CANCEL_URL,
                        "http://localhost:4200/" + SUCCESS_URL);
                for (Links link : payment.getLinks()) {
                    if (link.getRel().equals("approval_url")) {
                        String json = "{\"approval_url\":\"" + link.getHref() + "\"}";
                        return ResponseEntity.ok(json);
                    }
                }
            }

        } catch (PayPalRESTException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Admission>> viewAllAssociates(@RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Retrieve the list of all admissions
        List<Admission> associateList = admissionService.viewAll();

        // Return the list of admissions in the response body
        return ResponseEntity.ok(associateList);
    }

    // @GetMapping(value = CANCEL_URL)
    // public String cancelPay() {
    //     return "cancel";
    // }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}