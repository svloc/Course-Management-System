package com.cms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired AdmissionRepository admissionRepository;

	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		
		return null;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		return 0;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		return null;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		return null;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		return null;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
				
		return false;
	}

	public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		return false;
	}

	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}

}
