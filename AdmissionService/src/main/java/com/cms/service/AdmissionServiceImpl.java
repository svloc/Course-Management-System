package com.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
// import com.cms.model.Course;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	AdmissionRepository admissionRepository;

	public Admission registerAssociateForCourse(Admission admission) throws AdmissionInvalidException {

		return null;
	}

	public int calculateFees(String associateId) throws AdmissionInvalidException {
		List<Admission> admission = admissionRepository.findCoursesByAssociateId(associateId);
		int totalFees = 0;
		for (Admission admissionObj : admission) {
			totalFees += admissionObj.getFees();
		}
		return totalFees;
	}

	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		return null;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId) throws AdmissionInvalidException {
		List<Admission> admission = admissionRepository.findCoursesByAssociateId(associateId);
		if (admission.isEmpty()) {
			throw new AdmissionInvalidException("No registered courses found for the associate.");
		}

		double highestFee = 0.0;
		for (Admission admissionObj : admission) {
			if (admissionObj.getFees() > highestFee) {
				highestFee = admissionObj.getFees();
			}
		}

		List<String> coursesWithHighestFee = new ArrayList<>();

		for (Admission admissionObj : admission) {
			if (admissionObj.getFees() == highestFee) {
				coursesWithHighestFee.add(admissionObj.getCourseId());
			}
		}

		return coursesWithHighestFee;
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		List<Admission> admissions = admissionRepository.findByCourseId(courseId);
		if (admissions.isEmpty()) {
			throw new AdmissionInvalidException("No admissions found for the given course ID.");
		}

		List<String> feedbackList = new ArrayList<>();
		for (Admission admission : admissions) {
			feedbackList.add(admission.getFeedback());
		}

		return feedbackList;
	}

	public boolean deactivateAdmission(String courseId) throws AdmissionInvalidException {
		List<Admission> admissions = admissionRepository.findByCourseId(courseId);
		if (admissions.isEmpty()) {
			throw new AdmissionInvalidException("No admissions found for the given course ID.");
		}

		admissionRepository.deleteByCourseId(courseId);
		return true;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException {
		return false;
	}

	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}

}
