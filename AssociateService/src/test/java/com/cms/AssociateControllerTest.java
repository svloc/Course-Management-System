package com.cms;

import com.cms.controller.AssociateController;
import com.cms.model.Associate;
import com.cms.service.AssociateServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.junit.MockitoJUnitRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
public class AssociateControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AssociateServiceImpl associateService;

	@InjectMocks
	private AssociateController associateController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(associateController).build();
	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successfull

	@Test
	public void test115RestApiCallForViewByAssociateId() throws Exception {
		// Mock the behavior of the associateService
		when(associateService.viewByAssociateId(anyString())).thenReturn(new Associate());

		mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", "1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
				// .andExpect(jsonPath("$.associateId").exists());
	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful
	@Test
	public void test116RestApiCallForUpdateAssociate() throws Exception {
		when(associateService.updateAssociate(anyString(),anyString())).thenReturn(new Associate());
		mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddr}", "1", "New Address")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(associateService).updateAssociate(eq("1"), eq("New Address"));
	}

	// 3.Test whether the endpoint /addAssociate is successful
	@Test
	public void test117RestApiCallForAddAssociate() throws Exception {
		Associate associate = new Associate("12345", "associateName", "associateAddress", "associateEmailId");

		// Convert the Associate object to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		String requestBody = objectWriter.writeValueAsString(associate);

		// Mock the behavior of the associateService
		when(associateService.addAssociate(any(Associate.class))).thenReturn(associate);

		mockMvc.perform(post("/associate/addAssociate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isOk());

		verify(associateService).addAssociate(associate);
	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successful for
	// invalid token
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidToken() {

	}

	// Test whether the endpoint /viewByAssociateId/{associateId} is successful for
	// invalid id
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidId()  throws Exception {
        when(associateService.updateAssociate(anyString(), anyString())).thenReturn(null); // Return null to indicate invalid ID

        mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddr}", "invalidId", "New Address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(associateService).updateAssociate(eq("invalidId"), eq("New Address"));
	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful for invalid token
	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidToken() {

	}

	// Test whether the end point /updateAssociate/{associateId}/{associateAddress}
	// is successful for invalid id
    @Test
	public void test116RestApiCallForUpdateAssociateForInvalidId() throws Exception{
		when(associateService.viewByAssociateId(anyString())).thenReturn(null); // Return null to indicate invalid ID

        mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", "invalidId"))
                .andExpect(status().isNotFound());

	}
    @Test
	public void test117RestApiCallForAddAssociateForInvalidToken() {

	}

}
