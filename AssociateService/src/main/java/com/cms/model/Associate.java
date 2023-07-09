package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "associate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Associate {
	@Id
	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;

}