package com.cms;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.proxy.CourseProxy;
import com.cms.repository.AdmissionRepository;
import com.cms.service.AdmissionServiceImpl;
import com.cms.service.IAdmissionService;

//Write Unit Tests for the methods in the AdmissionServiceImpl

public class AdmissionServiceImplTest {
	@Mock
	private AdmissionRepository admissionRepository;

	@InjectMocks
	private AdmissionServiceImpl admissionServiceImpl;

	@Mock
	private IAdmissionService admissionService;
	@Mock
	private CourseProxy courseProxy;

	@Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
	// check whether the calculateFees calculates fees for the given associate Id
	@Test
	public void test108CalculateFees() {
		String associateId = "100";
		int expectedFees = 5000;
		try {
			when(admissionService.calculateFees(associateId)).thenReturn(expectedFees);
			int fees = admissionServiceImpl.calculateFees(associateId);
			assertEquals(expectedFees, fees);
			verify(admissionService, times(1)).calculateFees(associateId);
		} catch (AdmissionInvalidException e) {
			e.printStackTrace();
		}

	}

	// check whether addFeedback adds the feedback entered by the assocaite for the
	// given registration Id
	@Test
	public void test109AddFeedback() {
		Long regNo = 303L;
		String feedback = "Good course";
		float feedbackRating = 4.5f;

		Admission admission = new Admission();
		admission.setRegistrationId(regNo);
		admission.setFeedback(feedback);
		try {
			when(admissionService.addFeedback(regNo, feedback, feedbackRating)).thenReturn(admission);
			Admission result = admissionServiceImpl.addFeedback(regNo, feedback, feedbackRating);
			assertEquals(admission, result);
			verify(admissionService, times(1)).addFeedback(regNo, feedback, feedbackRating);
		} catch (AdmissionInvalidException e) {
			e.printStackTrace();
		}

	}

	// check whether highestFeeForTheRegisteredCourse returns the highest fee among
	// all the courses registered by the associate
	@Test
	public void test110highestFeeForTheRegisteredCourse() {
		String associateId = "A001";
		List<String> courses = new ArrayList<>();
		courses.add("Course 1");
		courses.add("Course 2");
		courses.add("Course 3");

		try {
			when(admissionService.highestFeeForTheRegisteredCourse(associateId)).thenReturn(courses);

			List<String> result = admissionServiceImpl.highestFeeForTheRegisteredCourse(associateId);

			assertEquals(courses, result);
			verify(admissionService, times(1)).highestFeeForTheRegisteredCourse(associateId);
		} catch (AdmissionInvalidException e) {
			e.printStackTrace();
		}
	}

	// check whether the viewFeedbackByCourseId returns the list of feedbacks for
	// the given courseId
	@Test
	public void test111ViewFeedbackByCourseId() {
		String courseId = "203";
		List<String> feedbacks = new ArrayList<>();
		feedbacks.add("Feedback 1");
		try {
			when(admissionService.viewFeedbackByCourseId(courseId)).thenReturn(feedbacks);

			List<String> result = admissionServiceImpl.viewFeedbackByCourseId(courseId);

			assertEquals(feedbacks, result);
			verify(admissionService, times(1)).viewFeedbackByCourseId(courseId);
		} catch (AdmissionInvalidException e) {
			e.printStackTrace();
		}
	}

	// check whether deactivateAdmission removes the admission for the given
	// courseId in the database
	@Test
	public void test112DeactivateAdmission() {
		String courseId = "203";
		try {
			when(admissionService.deactivateAdmission(courseId)).thenReturn(true);

			boolean result = admissionServiceImpl.deactivateAdmission(courseId);

			assertTrue(result);
			verify(admissionService, times(1)).deactivateAdmission(courseId);
		} catch (AdmissionInvalidException e) {
			e.printStackTrace();
		}
	}

	// check whether addFeedback throws AdmissionInvalidException when the
	// registration id is invalid
	@Test
	public void test113AddFeedbackForInvalidId() {

	}

	// check whether viewFeedbackByCourseId throws AdmissionInvalidException when
	// the course id is invalid
	@Test
	public void test114ViewFeedbackByCourseIdForInvalidCourseId() {

	}

	// check whether the calculateFees throws AdmissionInvalidException for invalid
	// associate Id
	@Test
	public void test115calculateFeesForInvalidAssociateId() {

	}

	// check whether the highestFeeForTheRegisteredCourse throws
	// AdmissionInvalidException for invalid associate Id
	@Test
	public void test116highestFeeForTheRegisteredCourseForInvalidAssociateId() {

	}

	// check whether the deactivateAdmission throws AdmissionInvalidException for
	// invalid course Id
	@Test
	public void test117deactivateAdmissionForInvalidCourseId() {

	}

}
