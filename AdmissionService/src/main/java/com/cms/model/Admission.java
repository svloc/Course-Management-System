package com.cms.model;

import java.io.Serializable;

public class Admission implements Serializable {

	private long registrationId;
	private String courseId;
	private String associateId;
	private int fees;
	private String feedback;
	public long getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
