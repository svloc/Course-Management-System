package com.cms;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.CourseController;
import com.cms.model.Course;
import com.cms.service.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

//Write mockito tests for the endpoints in the CourseController

@RunWith(SpringRunner.class)
public class CourseControllerTest {
	private MockMvc mockMvc;

	@Mock
	private CourseServiceImpl courseService;

	@InjectMocks
	private CourseController courseController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
	}

	// 1.Test whether the endpoint /viewByCourseId/{courseId} is successful
	@Test
	public void test122RestApiCallForViewByCourseId() throws Exception{
		when(courseService.viewByCourseId(anyString())).thenReturn(new Course());

        mockMvc.perform(get("/course/viewByCourseId/{courseId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	// 2.Test whether the end point /update/{courseId}/{fee} is successfull
	@Test
	public void test123RestApiCallForUpdateCourse() throws Exception {
		when(courseService.updateCourse(anyString(),anyInt())).thenReturn(new Course());
		mockMvc.perform(put("/course/update/{courseId}/{duration}", "1", 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(courseService).updateCourse(eq("1"), eq(100));
	}

	// 3. Test whether the endpoint /viewByCourseId/{courseId} is successfull
	@Test
	public void test124RestApiCallForViewFeedbackRating() throws Exception {
		when(courseService.findFeedbackRatingForCourseId(anyString())).thenReturn(4.5f);

        mockMvc.perform(get("/course/viewRatingByCourseId/{courseId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                // .andExpect(jsonPath("$.rating").value(4.5));
	}

	// 4. Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull
	@Test
	public void test125RestApiCallForCalculateAverageFeedback() throws Exception {

		when(courseService.calculateAverageFeedbackAndUpdate(anyString(),anyFloat())).thenReturn(new Course());

        mockMvc.perform(put("/course/calculateAverageFeedback/{courseId}/{rating}", "1", 4.5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Optionally, you can verify that the courseService's method was called with the correct arguments
        verify(courseService).calculateAverageFeedbackAndUpdate(eq("1"), eq(4.5f));
	}

	// 5. Test whether the endpoint /addCourse is successfull
	@Test
	public void test126RestApiCallForAddCourse() throws Exception {
		Course course = new Course("1", "Course Name", 1000, 10, "Course Type", 4.5f);

		mockMvc.perform(post("/course/addCourse")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(course)))
				.andExpect(status().isOk());

		// Optionally, you can verify that the courseService's method was called
		verify(courseService).addCourse(any(Course.class));
	}

	// 6. Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid id
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidId() throws Exception {
        when(courseService.viewByCourseId(anyString())).thenReturn(null); // Return null to indicate invalid ID

        mockMvc.perform(get("/course/viewByCourseId/{courseId}", "invalidId"))
                .andExpect(status().isNotFound());
	}

	// 7.Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid token
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidToken() throws Exception {

	}

	// 8.Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid id
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidId() throws Exception {
		mockMvc.perform(put("/course/update/{courseId}/{fee}", "invalidId", 100)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	// Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid token
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidToken() throws Exception {

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid id
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidId() throws Exception {

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid token
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidToken() throws Exception {

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid id
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidId() throws Exception {

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid token
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidToken() throws Exception {

	}

	// Test whether the endpoint /addCourse is successfull for invalid token
	@Test
	public void test126RestApiCallForAddCourseForInvalidToken() throws Exception {

	}

}
