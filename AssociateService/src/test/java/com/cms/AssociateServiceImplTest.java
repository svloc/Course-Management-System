package com.cms;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;
import com.cms.service.AssociateServiceImpl;

import java.util.Optional;

public class AssociateServiceImplTest {

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateServiceImpl associateService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    

    // 1.check whether the addAssociate persists the associate object in the database
    @Test
    public void test118AddAssociate() {
        // Create a mock Associate object
        Associate associate = new Associate();
        associate.setAssociateId("1");
        associate.setAssociateName("John Doe");
        
        // Mock the associateRepository.save() method to return the associate
        Mockito.when(associateRepository.save(Mockito.any(Associate.class))).thenReturn(associate);
        
        try {
            // Call the addAssociate method
            Associate result = associateService.addAssociate(associate);
            
            // Verify that the associateRepository.save() method was called
            Mockito.verify(associateRepository).save(associate);
            
            // Assert that the returned associate is the same as the mock
            assertEquals(associate, result);
        } catch (AssociateInvalidException e) {
            fail("Should not throw an exception");
        }
    }


	//2.check whether the viewByAssociateId returns the associate for the given associate Id
    @Test
    public void test119ViewByAssociateId() {
        // Create a mock Associate object
        Associate associate = new Associate();
        associate.setAssociateId("1");
        associate.setAssociateName("John Doe");
        
        // Mock the associateRepository.findById() method to return the associate
        Mockito.when(associateRepository.findById("1")).thenReturn(Optional.of(associate));
        
        try {
            // Call the viewByAssociateId method
            Associate result = associateService.viewByAssociateId("1");
            
            // Verify that the associateRepository.findById() method was called
            Mockito.verify(associateRepository).findById("1");
            
            // Assert that the returned associate is the same as the mock
            assertEquals(associate, result);
        } catch (AssociateInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    //3.check whether updateAssociate updates the address of the given assciateId in the database
    @Test
    public void test120updateAssociate() {
        // Create a mock Associate object
        Associate associate = new Associate();
        associate.setAssociateId("1");
        associate.setAssociateName("John Doe");
        
        // Mock the associateRepository.findById() method to return the associate
        Mockito.when(associateRepository.findById("1")).thenReturn(Optional.of(associate));
        
        try {
            // Call the updateAssociate method
            Associate result = associateService.updateAssociate("1", "New Address");
            
            // Verify that the associateRepository.findById() method was called
            Mockito.verify(associateRepository).findById("1");
            
            // Verify that the associateRepository.save() method was called
            Mockito.verify(associateRepository).save(associate);
            
            // Assert that the returned associate is the same as the mock
            assertEquals(associate, result);
            
            // Assert that the associate's address was updated
            assertEquals("New Address", associate.getAssociateAddress());
        } catch (AssociateInvalidException e) {
            fail("Should not throw an exception");
        }
    }


	// 4.check whether viewByAssociateId throws AssociateInvalidException for invalid associateId
	@Test(expected = AssociateInvalidException.class)
    public void test121ViewByAssociateIdForInvalidId() throws AssociateInvalidException {
        // Mock the associateRepository.findById() method to return null
        Mockito.when(associateRepository.findById("invalidId")).thenReturn(Optional.empty());
        
        // Call the viewByAssociateId method with an invalid ID
        associateService.viewByAssociateId("invalidId");
        
        // Verify that the associateRepository.findById() method was called
        Mockito.verify(associateRepository).findById("invalidId");
    }


	// 5.check whether updateAssociate updates the address of the given assciateId in the database for invalid id
    @Test(expected = AssociateInvalidException.class)
    public void test120UpdateassociateForInvalidId() throws AssociateInvalidException {
        // Mock the associateRepository.findById() method to return null
        Mockito.when(associateRepository.findById("invalidId")).thenReturn(Optional.empty());
        
        // Call the updateAssociate method with an invalid ID
        associateService.updateAssociate("invalidId", "New Address");
        
        // Verify that the associateRepository.findById() method was called
        Mockito.verify(associateRepository).findById("invalidId");
    }
}