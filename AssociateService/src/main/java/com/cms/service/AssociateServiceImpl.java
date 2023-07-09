package com.cms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

@Service
@Transactional
public class AssociateServiceImpl implements IAssociateService {

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private SequenceGeneratorService seqGeneratorService;

    /**
     * Adds a new Associate.
     * 
     * @param aObj The Associate object to be added.
     * @return The added Associate object.
     * @throws AssociateInvalidException If the AssociateId already exists.
     */
    public Associate addAssociate(Associate aObj) throws AssociateInvalidException {
        Associate associate;
        try {
            aObj.setAssociateId(seqGeneratorService.generateAssociateId());
            associate = associateRepository.save(aObj);
        } catch (IllegalArgumentException e) {
            throw new AssociateInvalidException("AssociateId already exists");
        }
        return associate;
    }

    /**
     * Retrieves an Associate by AssociateId.
     * 
     * @param associateId The AssociateId of the Associate.
     * @return The retrieved Associate object.
     * @throws AssociateInvalidException If the AssociateId is invalid.
     */
    public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
        Associate associate = associateRepository.findById(associateId).orElse(null);
        if (associate == null) {
            throw new AssociateInvalidException("Invalid Associate Id");
        }
        return associate;
    }

    /**
     * Updates the address of an Associate.
     * 
     * @param associateId       The AssociateId of the Associate to be updated.
     * @param associateAddress The new address of the Associate.
     * @return The updated Associate object.
     * @throws AssociateInvalidException If the AssociateId does not exist.
     */
    public Associate updateAssociate(String associateId, String associateAddress) throws AssociateInvalidException {
        Associate associate = associateRepository.findById(associateId).orElse(null);
        if (associate == null) {
            throw new AssociateInvalidException("AssociateId does not exist");
        } else {
            associate.setAssociateAddress(associateAddress);
            associateRepository.save(associate);
            return associate;
        }
    }

    /**
     * Retrieves all Associates.
     * 
     * @return The list of all Associates.
     */
    public List<Associate> viewAll() {
        return associateRepository.findAll();
    }
}
