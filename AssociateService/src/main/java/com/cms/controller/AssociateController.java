package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.AssociateServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/associate")
public class AssociateController {
    @Autowired
    AssociateServiceImpl associateService;
    @Autowired
    AuthenticationAuthorizationProxy authService;

    @PostMapping("/addAssociate")
    public ResponseEntity<?> addAssociate(@RequestBody Associate associate,
            @RequestHeader("Authorization") String authorization) {
        // Check if the token is valid using the AuthenticationAuthorizationProxy
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Associate associateObj = associateService.addAssociate(associate);
            return ResponseEntity.ok(associateObj);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateAssociate/{associateId}/{address}")
    public ResponseEntity<?> updateAssociate(@PathVariable("associateId") String associateId,
            @PathVariable("address") String address, @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Associate updatedAssociate = associateService.updateAssociate(associateId, address);
            return ResponseEntity.ok(updatedAssociate);
            
        } catch (AssociateInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewByAssociateId/{associateId}")
    public ResponseEntity<?> viewByAssociateId(@PathVariable("associateId") String associateId,
            @RequestHeader("Authorization") String authorization) {
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Associate associate = associateService.viewByAssociateId(associateId);
            return ResponseEntity.ok(associate);

        } catch (AssociateInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Associate>> viewAllAssociates(@RequestHeader("Authorization") String authorization) {
        // Check if the token is valid using the AuthenticationAuthorizationProxy
        if (!authService.isValidToken(authorization)) {
            // If the token is not valid, return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // If the token is valid, proceed with retrieving all associates
        try {
            List<Associate> associateList = associateService.viewAll();
            return ResponseEntity.ok(associateList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
