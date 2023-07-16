package com.cms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "admission")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Admission implements Serializable {
	@Transient
    public static final String SEQUENCE_NAME = "admission_sequence";
	@Id
	private long registrationId;
	private String courseId;
	private String associateId;
	private int fees;
	private String feedback;
	private float rating;
}