
package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
	@Id
	private String courseId;
	private String courseName;
	private Integer fees;
	private Integer duration;
	private String courseType;
	private float rating;	
}
