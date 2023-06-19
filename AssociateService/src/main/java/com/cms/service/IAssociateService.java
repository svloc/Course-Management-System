package com.cms.service;

import java.util.List;

// import org.springframework.web.bind.annotation.PathVariable;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;

public interface IAssociateService {
	public Associate addAssociate(Associate cObj)throws AssociateInvalidException;
	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException;
	public Associate updateAssociate(String associateId,String associateAddress)throws AssociateInvalidException; 
	public List<Associate> viewAll();
}
