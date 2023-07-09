package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
	private String associateId;
	private String associateName;
	private String associateAddress;
	private String associateEmailId;
}
