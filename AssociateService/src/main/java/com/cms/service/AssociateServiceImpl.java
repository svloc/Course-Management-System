package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;



@Service
public class AssociateServiceImpl implements IAssociateService{
	
	
	public Associate addAssociate(Associate cObj) {
		
		return null;
	}

	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		return null;
	}

	public Associate updateAssociate(String associateId, String associateAddress)throws AssociateInvalidException {
		return null;
	}

	
	public List<Associate> viewAll() {
		return null;
	}

}

