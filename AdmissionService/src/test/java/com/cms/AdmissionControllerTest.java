package com.cms;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AdmissionController;
import com.cms.service.AdmissionServiceImpl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//Write mockito tests for the endpoints in the AdmissionController

@WebMvcTest(AdmissionController.class)
public class AdmissionControllerTest {
	private MockMvc mockMvc;
    @Mock
    private AdmissionServiceImpl admissionService;

    @InjectMocks
    private AdmissionController admissionController;

	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(admissionController).build();
    }


	//Test whether the endpoint /highestFee/{associateId} is successful
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourse() throws Exception{
		 String associateId = "A001";
        List<String> courses = Arrays.asList("Course 1", "Course 2", "Course 3");

        when(admissionService.highestFeeForTheRegisteredCourse(associateId)).thenReturn(courses);

        mockMvc.perform(get("/highestFee/{associateId}", associateId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Course 1"))
                .andExpect(jsonPath("$[1]").value("Course 2"))
                .andExpect(jsonPath("$[2]").value("Course 3"));

        verify(admissionService, times(1)).highestFeeForTheRegisteredCourse(associateId);
        verifyNoMoreInteractions(admissionService);
	}
	
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful
	@Test
	public void test102RestApiCallForViewFeedbackByCourseId() throws Exception{
		String courseId = "C001";
        List<String> feedbacks = Arrays.asList("Feedback 1", "Feedback 2", "Feedback 3");

        when(admissionService.viewFeedbackByCourseId(courseId)).thenReturn(feedbacks);

        mockMvc.perform(get("/viewFeedbackByCourseId/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Feedback 1"))
                .andExpect(jsonPath("$[1]").value("Feedback 2"))
                .andExpect(jsonPath("$[2]").value("Feedback 3"));

        verify(admissionService, times(1)).viewFeedbackByCourseId(courseId);
        verifyNoMoreInteractions(admissionService);
	}
	
    // Test whether the end point /calculateFees/{associateId} is successful
	@Test
	public void test107RestApiCallForCalculateFees() throws Exception{
		String associateId = "A001";
        int fees = 5000;

        when(admissionService.calculateFees(associateId)).thenReturn(fees);

        mockMvc.perform(get("/calculateFees/{associateId}", associateId))
                .andExpect(status().isOk())
                .andExpect(content().string("5000"));

        verify(admissionService, times(1)).calculateFees(associateId);
        verifyNoMoreInteractions(admissionService);
	}
	
	// Test whether the end point /highestFee/{associateId} is successful for invalid id
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidId() throws Exception{
		
	}
	
	// Test whether the end point /highestFee/{associateId} is successful for invalid token
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidToken() throws Exception{
		
	}
	
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful for invalid id
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidId() throws Exception {
		
	}
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful for invalid token
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidToken() throws Exception {
		
	}
	
	
	//Test whether the end point /deactivate/{courseId} is successful for invalid id
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidId() throws Exception {
		
	}
	
	//Test whether the end point /deactivate/{courseId} is successful for invalid token
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidToken() throws Exception {

	}
	
	
	//Test whether the end point /calculateFees/{associateId} is successful for invalid id
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidId() throws Exception {
		
	}
	
	//Test whether the end point /calculateFees/{associateId} is successful for invalid token
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidToken() throws Exception {
		
	}

}
