package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	private String courseId;
	private String courseName;
	private Integer fees;
	private Integer duration;
	private String courseType;
	private float rating;
}
