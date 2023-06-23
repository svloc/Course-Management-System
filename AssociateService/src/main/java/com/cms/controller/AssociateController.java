package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.AssociateServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/associate")
public class AssociateController {
    @Autowired AssociateServiceImpl associateService;


    @PostMapping("/addAssociate")
	public ResponseEntity<?> addAssociate(@RequestBody Associate associate) {
		try {
			Associate associateObj = associateService.addAssociate(associate);
			return ResponseEntity.ok(associateObj);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

    @PutMapping("/updateAssociate/{associateId}/{associateAddr}")
    public ResponseEntity<Associate> updateAssociate(@PathVariable("associateId") String associateId,@PathVariable("associateAddr") String associateAddr) {
        try {
            Associate updatedAssociate = associateService.updateAssociate(associateId, associateAddr);
            if (updatedAssociate != null) {
                return ResponseEntity.ok(updatedAssociate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (AssociateInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewByAssociateId/{associateId}")
    public ResponseEntity<Associate> viewByAssociateId(@PathVariable("associateId") String associateId) {
        try {
            Associate associate = associateService.viewByAssociateId(associateId);
            if (associate != null) {
                return ResponseEntity.ok(associate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (AssociateInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Associate>> viewAllAssociates() {
        List<Associate> associateList = associateService.viewAll();
        return ResponseEntity.ok(associateList);
    }
}



