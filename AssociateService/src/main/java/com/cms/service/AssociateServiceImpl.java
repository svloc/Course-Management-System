package com.cms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AssociateServiceImpl implements IAssociateService {

	@Autowired
	private AssociateRepository associateRepository;

	
	public Associate addAssociate(Associate aObj) throws AssociateInvalidException {
		Associate associate;
		try {
			associate = associateRepository.save(aObj);
			log.info("The method addAssociate has completed successfully");
		} catch (IllegalArgumentException e) {
			log.error("AssociateId already exists");
			throw new AssociateInvalidException("AssociateId already exists");
		}
		return associate;

	}

	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		Associate associate = associateRepository.findById(associateId).orElse(null);
		if (associate == null) {
			log.error("AssociateId already exists");
			throw new AssociateInvalidException("AssociateId does not exists");
		}
		log.info("The method viewByAssociateId has completed successfully");
		return associate;
	}

	public Associate updateAssociate(String associateId, String associateAddress) throws AssociateInvalidException {
		Associate associate = associateRepository.findById(associateId).orElse(null);
		if (associate == null) {
			log.error("AssociateId already exists");
			throw new AssociateInvalidException("AssociateId does not exists");
		} else {
			associate.setAssociateAddress(associateAddress);
			associateRepository.save(associate);
			log.info("The method updateAssociate has completed successfully");
			return associate;
		}

	}

	public List<Associate> viewAll() {
		List<Associate> associateList = null;

		try {
			associateList = associateRepository.findAll();
			log.info("The method viewAll has completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return associateList;

	}

}
