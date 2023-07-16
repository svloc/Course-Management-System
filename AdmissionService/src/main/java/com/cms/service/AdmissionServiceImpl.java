package com.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
// import com.cms.payment.Order;
// import com.cms.payment.PaypalService;
import com.cms.repository.AdmissionRepository;
// import com.paypal.api.payments.Links;
// import com.paypal.api.payments.Payment;
// import com.paypal.base.rest.PayPalRESTException;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;
	@Autowired
	private SequenceGeneratorService seqGeneratorService;
	// @Autowired
	// PaypalService paypalService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	public Admission registerAssociateForCourse(Admission admission) throws AdmissionInvalidException {
		List<Admission> admissionsList = admissionRepository.findCoursesByAssociateId(admission.getAssociateId());

		Admission admission2;

		try {
			// Generate a new registrationId using the sequence generator service
			if (admissionsList.isEmpty()) {
				admission.setRegistrationId(seqGeneratorService.generateAdmissionId());
				// Save the admission details to the repository
				admission2 = admissionRepository.save(admission);
			} else {
				throw new AdmissionInvalidException("Associate Id already exists.");
			}
		} catch (IllegalArgumentException e) {
			// If the registrationId already exists, throw an AdmissionInvalidException
			throw new AdmissionInvalidException("AdmissionId already exists");
		}

		// Return the saved admission object with the newly generated registrationId
		return admission2;
	}

	public int calculateFees(String associateId) throws AdmissionInvalidException {
		try {
			// Find admissions by associateId from the admission repository
			List<Admission> admissions = admissionRepository.findCoursesByAssociateId(associateId);
			if (admissions == null) {
				// Throw AdmissionInvalidException if admissions is null
				throw new AdmissionInvalidException("AssociateId does not exists");
			}
			int totalFees = 0;
			// Iterate through each admission and sum up the fees
			for (Admission admission : admissions) {
				totalFees += admission.getFees();
			}
			// Return the total fees calculated
			return totalFees;
		} catch (Exception e) {
			// Throw AdmissionInvalidException if any exception occurs during the
			// calculation
			throw new AdmissionInvalidException(e.getMessage());
		}
	}

	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		// Find the admission by registration number
		Admission admission = admissionRepository.findById(regNo).orElse(null);
		// Check if the admission exists
		if (admission == null) {
			throw new AdmissionInvalidException("Invalid Registration Id");
		}
		// Set the feedback for the admission
		admission.setFeedback(feedback);
		// Save the updated admission
		return admissionRepository.save(admission);
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId) throws AdmissionInvalidException {
		// Find the admissions for the given associateId
		List<Admission> admissions = admissionRepository.findCoursesByAssociateId(associateId);
		// Check if any admissions exist for the associate
		if (admissions.isEmpty()) {
			throw new AdmissionInvalidException("No registered courses found for the associate.");
		}
		// Find the highest fee among the admissions
		double highestFee = 0.0;
		for (Admission admission : admissions) {
			if (admission.getFees() > highestFee) {
				highestFee = admission.getFees();
			}
		}
		// Collect the courseIds with the highest fee
		List<String> coursesWithHighestFee = new ArrayList<>();
		for (Admission admission : admissions) {
			if (admission.getFees() == highestFee) {
				coursesWithHighestFee.add(admission.getCourseId());
			}
		}
		return coursesWithHighestFee;
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		// Retrieve the admissions for the given courseId
		List<Admission> admissions = admissionRepository.findByCourseId(courseId);

		// Check if any admissions exist for the courseId
		if (admissions.isEmpty()) {
			throw new AdmissionInvalidException("No admissions found for the given course ID.");
		}

		// Collect the feedback from the admissions into a list
		List<String> feedbackList = new ArrayList<>();
		for (Admission admission : admissions) {
			feedbackList.add(admission.getFeedback());
		}

		return feedbackList;
	}

	public boolean deactivateAdmission(String courseId) throws AdmissionInvalidException {
		// Retrieve the admissions associated with the given courseId
		List<Admission> admissions = admissionRepository.findByCourseId(courseId);

		// Check if any admissions exist for the courseId
		if (admissions.isEmpty()) {
			throw new AdmissionInvalidException("No admissions found for the given course ID.");
		}

		// Delete the admissions with the given courseId
		admissionRepository.deleteByCourseId(courseId);

		// Return true to indicate successful deactivation
		return true;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException {
		Admission admission = admissionRepository.findById((long) registartionId).orElse(null);
		boolean result = true;
		if (admission == null) {
			result = false;
			throw new AdmissionInvalidException("Invalid Registration Id");
		}
		return result;
	}

	// public boolean makePayment(int registartionId) throws
	// AdmissionInvalidException {
	// Admission admission =
	// admissionRepository.findById((long)registartionId).orElse(null);
	// // Check if the admission exists
	// if (admission == null) {
	// throw new AdmissionInvalidException("Invalid Registration Id");
	// }
	// Order order = new Order(admission.getFees(), "USD", "paypal", "sale",
	// "Product 1");

	// try {
	// Payment payment = paypalService.createPayment(order.getPrice(),
	// order.getCurrency(), order.getMethod(),
	// order.getIntent(), order.getDescription(), "http://localhost:9093/" +
	// CANCEL_URL,
	// "http://localhost:9093/" + SUCCESS_URL);
	// for (Links link : payment.getLinks()) {
	// if (link.getRel().equals("approval_url")) {
	// String a = "redirect:" + link.getHref();
	// if (!a.isEmpty() && !link.getHref().isEmpty()) {
	// return true;
	// }
	// }
	// }

	// } catch (PayPalRESTException e) {

	// e.printStackTrace();
	// }
	// return false;
	// }

	// @GetMapping(value = CANCEL_URL)
	// public String cancelPay() {
	// return "cancel";
	// }

	// @GetMapping(value = SUCCESS_URL)
	// public String successPay(@RequestParam("paymentId") String paymentId,
	// @RequestParam("PayerID") String payerId) {
	// try {
	// Payment payment = paypalService.executePayment(paymentId, payerId);
	// System.out.println(payment.toJSON());
	// if (payment.getState().equals("approved")) {
	// return "success";
	// }
	// } catch (PayPalRESTException e) {
	// System.out.println(e.getMessage());
	// }
	// return "redirect:/";
	// }

	public List<Admission> viewAll() {
		// Retrieve all admissions from the repository
		return admissionRepository.findAll();
	}

}
