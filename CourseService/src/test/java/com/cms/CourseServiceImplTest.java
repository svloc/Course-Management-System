 package com.cms;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
import com.cms.service.CourseServiceImpl;
import java.util.Optional;

//Write Unit Tests for the methods in the CourseServiceImpl
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

	// 1. check whether the addCourse persists the course object in the database
    @Test
    public void test127AddCourse() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");

        // Mock the courseRepository.save() method to return the course
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);

        try {
            // Call the addCourse method
            Course result = courseService.addCourse(course);

            // Verify that the courseRepository.save() method was called
            Mockito.verify(courseRepository).save(course);

            // Assert that the returned course is the same as the mock
            assertEquals(course, result);
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    // 2. check whether viewByCourseId returns the course for the given courseId
    @Test
    public void test128ViewByCourseId() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");

        // Mock the courseRepository.findById() method to return the course
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        try {
            // Call the viewByCourseId method
            Course result = courseService.viewByCourseId("1");

            // Verify that the courseRepository.findById() method was called
            Mockito.verify(courseRepository).findById("1");

            // Assert that the returned course is the same as the mock
            assertEquals(course, result);
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

	// 3. check whether the findFeedbackRatingForCourseId	returns the feedback rating for the given courseId
    @Test
    public void test129FindFeedbackRatingForCourseId() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");
        course.setRating(4.5f);

        // Mock the courseRepository.findById() method to return the course
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        try {
            // Call the findFeedbackRatingForCourseId method
            float result = courseService.findFeedbackRatingForCourseId("1");

            // Verify that the courseRepository.findById() method was called
            Mockito.verify(courseRepository).findById("1");

            // Assert that the returned rating is the same as the mock
            assertEquals(4.5f, result, 0.01);
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    // 4. check whether updateCourse updates the fees for the given courseId in the database
	@Test
    public void test130UpdateCourse() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");

        // Mock the courseRepository.findById() method to return the course
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        try {
            // Call the updateCourse method
            Course result = courseService.updateCourse("1", 2000);

            // Verify that the courseRepository.findById() method was called
            Mockito.verify(courseRepository).findById("1");

            // Verify that the courseRepository.save() method was called
            Mockito.verify(courseRepository).save(course);

            // Assert that the duration of the course has been updated
            assertEquals(2000, result.getDuration().intValue());
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    // 5. check whether the calculateAverageFeedbackAndUpdate calculates the average feedback rating with the given rating and existing rating in the database for the given courseId and updates in the database	
    @Test
    public void test131CalculateAverageFeedbackAndUpdate() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");
        course.setRating(4.0f);

        // Mock the courseRepository.existsById() method to return true
        Mockito.when(courseRepository.existsById("1")).thenReturn(true);

        // Mock the courseRepository.findById() method to return the course
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        try {
            // Call the calculateAverageFeedbackAndUpdate method
            Course result = courseService.calculateAverageFeedbackAndUpdate("1", 4.5f);

            // Verify that the courseRepository.existsById() method was called
            Mockito.verify(courseRepository).existsById("1");

            // Verify that the courseRepository.findById() method was called
            Mockito.verify(courseRepository).findById("1");

            // Verify that the courseRepository.save() method was called
            Mockito.verify(courseRepository).save(course);

            // Assert that the rating of the course has been updated
            assertEquals(4.5f, result.getRating(), 0.01);
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    // 6. check whether the deactivateCourse removes the course of the given courseId in the database	
	@Test
    public void test132DeactivateCourse() {
        // Create a mock Course object
        Course course = new Course();
        course.setCourseId("1");
        course.setCourseName("English");

        // Mock the courseRepository.existsById() method to return true
        Mockito.when(courseRepository.existsById("1")).thenReturn(true);

        // Mock the courseRepository.findById() method to return the course
        Mockito.when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        try {
            // Call the deactivateCourse method
            Course result = courseService.deactivateCourse("1");

            // Verify that the courseRepository.existsById() method was called
            Mockito.verify(courseRepository).existsById("1");

            // Verify that the courseRepository.findById() method was called
            Mockito.verify(courseRepository).findById("1");

            // Verify that the courseRepository.deleteById() method was called
            Mockito.verify(courseRepository).deleteById("1");

            // Assert that the deactivated course is the same as the mock
            assertEquals(course, result);
        } catch (CourseInvalidException e) {
            fail("Should not throw an exception");
        }
    }

    // 7. check whether viewByCourseId throws CourseInvalidException when an invalid courseid is passed	
	@Test(expected = CourseInvalidException.class)
    public void test133ViewByCourseIdForInvalidId() throws CourseInvalidException {
        // Mock the courseRepository.findById() method to return empty
        Mockito.when(courseRepository.findById("invalidId")).thenReturn(Optional.empty());

        // Call the viewByCourseId method with an invalid ID
        courseService.viewByCourseId("invalidId");

        // Verify that the courseRepository.findById() method was called
        Mockito.verify(courseRepository).findById("invalidId");
    }

    // 8. check whether updateCourse throws CourseInvalidException for invalid course id
	@Test(expected = CourseInvalidException.class)
    public void test135updateCourseInvalidId() throws CourseInvalidException {
        // Mock the courseRepository.findById() method to return empty
        Mockito.when(courseRepository.findById("invalidId")).thenReturn(Optional.empty());

        // Call the updateCourse method with an invalid ID
        courseService.updateCourse("invalidId", 2000);

        // Verify that the courseRepository.findById() method was called
        Mockito.verify(courseRepository).findById("invalidId");
    }

	// 9. check whether calculateAverageFeedbackAndUpdate throws CourseInvalidExcception for invalid course id
    @Test(expected = CourseInvalidException.class)
    public void test136calculateAverageFeedbackAndUpdateForInvalidId() throws CourseInvalidException {
        // Mock the courseRepository.existsById() method to return false
        Mockito.when(courseRepository.existsById("invalidId")).thenReturn(false);

        // Call the calculateAverageFeedbackAndUpdate method with an invalid ID
        courseService.calculateAverageFeedbackAndUpdate("invalidId", 4.5f);

        // Verify that the courseRepository.existsById() method was called
        Mockito.verify(courseRepository).existsById("invalidId");
    }

	// 10.check whether findFeedbackRating throws CourseInvalidExcception for invalid course id
    @Test(expected = CourseInvalidException.class)
    public void test137findFeedbackRatingForCourseIdForInvalidId() throws CourseInvalidException {
        // Mock the courseRepository.findById() method to return empty
        Mockito.when(courseRepository.findById("invalidId")).thenReturn(Optional.empty());

        // Call the findFeedbackRatingForCourseId method with an invalid ID
        courseService.findFeedbackRatingForCourseId("invalidId");

        // Verify that the courseRepository.findById() method was called
        Mockito.verify(courseRepository).findById("invalidId");
    }

    // 11. check whether deactivateCourse throws CourseInvalidExcception for invalid course id
	@Test(expected = CourseInvalidException.class)
    public void test138deactivateCourseForInvalidId() throws CourseInvalidException {
        // Mock the courseRepository.existsById() method to return false
        Mockito.when(courseRepository.existsById("invalidId")).thenReturn(false);

        // Call the deactivateCourse method with an invalid ID
        courseService.deactivateCourse("invalidId");

        // Verify that the courseRepository.existsById() method was called
        Mockito.verify(courseRepository).existsById("invalidId");
    }

}

// package com.cms;

// import com.cms.exception.CourseInvalidException;
// import com.cms.model.Course;
// import com.cms.service.CourseServiceImpl;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// //Write Unit Tests for the methods in the CourseServiceImpl

// public class CourseServiceImplTest {

//     // check whether the addCourse persists the course object in the database
//     @Test
//     public void test127AddCourse() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);
//             Assertions.assertNotNull(addedCourse.getCourseId());
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to add course: " + e.getMessage());
//         }
//     }

//     // check whether viewByCourseId returns the course for the given courseId
//     @Test
//     public void test128ViewByCourseId() {

//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);

//             Course viewedCourse = courseService.viewByCourseId(addedCourse.getCourseId());
//             Assertions.assertEquals(addedCourse.getCourseId(), viewedCourse.getCourseId());
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to view course by courseId: " + e.getMessage());
//         }

//     }

//     // check whether the findFeedbackRatingForCourseId returns the feedback rating
//     // for the given courseId
//     @Test
//     public void test129FindFeedbackRatingForCourseId() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);

//             float feedbackRating = courseService.findFeedbackRatingForCourseId(addedCourse.getCourseId());
//             Assertions.assertEquals(course.getRating(), feedbackRating);
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to find feedback rating for courseId: " + e.getMessage());
//         }

//     }

//     // check whether updateCourse updates the fees for the given courseId in the
//     // database
//     @Test
//     public void test130UpdateCourse() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);

//             Integer newFees = 1500;
//             Course updatedCourse = courseService.updateCourse(addedCourse.getCourseId(), newFees);
//             Assertions.assertEquals(newFees, updatedCourse.getFees());
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to update course: " + e.getMessage());
//         }
//     }

//     // check whether the calculateAverageFeedbackAndUpdate calculates the average
//     // feedback rating with the given rating and existing rating in the database for
//     // the given courseId and updates in the database
//     @Test
//     public void test131CalculateAverageFeedbackAndUpdate() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);

//             float newRating = 4.7f;
//             Course updatedCourse = courseService.calculateAverageFeedbackAndUpdate(addedCourse.getCourseId(),
//                     newRating);
//             Assertions.assertEquals(newRating, updatedCourse.getRating());
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to calculate average feedback and update course: " + e.getMessage());
//         }
//     }

//     // check whether the deactivateCourse removes the course of the given courseId
//     // in the database error
//     @Test
//     public void test132DeactivateCourse() {

//         CourseServiceImpl courseService = new CourseServiceImpl();

//         Course course = new Course();
//         course.setCourseName("Java Programming");
//         course.setFees(1000);
//         course.setDuration(30);
//         course.setCourseType("Programming");
//         course.setRating(4.5f);

//         try {
//             Course addedCourse = courseService.addCourse(course);

//             Course deactivatedCourse = courseService.deactivateCourse(addedCourse.getCourseId());
//             Assertions.assertNull(courseService.viewByCourseId(addedCourse.getCourseId()));
//         } catch (CourseInvalidException e) {
//             Assertions.fail("Failed to deactivate course: " + e.getMessage());
//         }
//     }

//     // check whether viewByCourseId throws CourseInvalidException when an invalid
//     // courseid is passed error
//     @Test
//     public void test133ViewByCourseIdForInvalidId() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         try {
//             // Passing an invalid courseId
//             Course course = courseService.viewByCourseId("invalidCourseId");
//             Assertions.fail("Expected CourseInvalidException but no exception was thrown.");
//         } catch (CourseInvalidException e) {
//             // Expected exception
//             Assertions.assertEquals("CourseId not found", e.getMessage());
//         }

//     }

//     // check whether updateCourse throws CourseInvalidException for invalid course
//     // id error
//     @Test
//     public void test135updateCourseInvalidId() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         try {
//             // Passing an invalid courseId
//             Course updatedCourse = courseService.updateCourse("invalidCourseId", 1500);
//             Assertions.fail("Expected CourseInvalidException but no exception was thrown.");
//         } catch (CourseInvalidException e) {
//             // Expected exception
//             Assertions.assertEquals("CourseId does not exists", e.getMessage());
//         }
//     }

//     // check whether calculateAverageFeedbackAndUpdate throws
//     // CourseInvalidExcception for invalid course id error
//     @Test
//     public void test136calculateAverageFeedbackAndUpdateForInvalidId() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         try {
//             // Passing an invalid courseId
//             Course updatedCourse = courseService.calculateAverageFeedbackAndUpdate("invalidCourseId", 4.7f);
//             Assertions.fail("Expected CourseInvalidException but no exception was thrown.");
//         } catch (CourseInvalidException e) {
//             // Expected exception
//             Assertions.assertEquals("CourseId does not exist", e.getMessage());
//         }
//     }

//     // check whether findFeedbackRating throws CourseInvalidExcception for invalid
//     // course id
//     @Test
//     public void test137findFeedbackRatingForCourseIdForInvalidId() {
//         CourseServiceImpl courseService = new CourseServiceImpl();

//         try {
//             // Passing an invalid courseId error
//             float feedbackRating = courseService.findFeedbackRatingForCourseId("invalidCourseId");
//             Assertions.fail("Expected CourseInvalidException but no exception was thrown.");
//         } catch (CourseInvalidException e) {
//             // Expected exception
//             Assertions.assertEquals("CourseId does not exist", e.getMessage());
//         }
//     }

//     // check whether deactivateCourse throws CourseInvalidExcception for invalid
//     // course id
//     @Test
// 	 public void test138deactivateCourseForInvalidId() {
// 		 CourseServiceImpl courseService = new CourseServiceImpl();

//         try {
//             // Passing an invalid courseId error
//             Course deactivatedCourse = courseService.deactivateCourse("invalidCourseId");
//             Assertions.fail("Expected CourseInvalidException but no exception was thrown.");
//         } catch (CourseInvalidException e) {
//             // Expected exception
//             Assertions.assertEquals("CourseId does not exist", e.getMessage());
//         }
// 	}

// }