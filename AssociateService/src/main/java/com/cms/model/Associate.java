package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "associate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
	@Id
	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;
	// public String getAssociateId() {
	// 	return associateId;
	// }
	// public void setAssociateId(String associateId) {
	// 	this.associateId = associateId;
	// }
	// public String getAssociateName() {
	// 	return associateName;
	// }
	// public void setAssociateName(String associateName) {
	// 	this.associateName = associateName;
	// }
	// public String getAssociateAddress() {
	// 	return associateAddress;
	// }
	// public void setAssociateAddress(String associateAddress) {
	// 	this.associateAddress = associateAddress;
	// }
	// public String getAssociateEmailId() {
	// 	return associateEmailId;
	// }
	// public void setAssociateEmailId(String associateEmailId) {
	// 	this.associateEmailId = associateEmailId;
	// }

}
