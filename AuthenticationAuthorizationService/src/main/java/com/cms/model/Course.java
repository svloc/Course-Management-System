
package com.cms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course implements Serializable {
	@Id
	private String courseId;

	private String courseName;

	private Integer fees;

	private Integer duration;

	private String courseType;
	private float rating;

}