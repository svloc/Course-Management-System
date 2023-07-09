package com.cms.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cms.model.Associate;

@FeignClient(value = "associateservice", url = "http://localhost:9092")
public interface ServiceProxy {
	@PostMapping(value = "/associate/addAssociate", produces = "application/json")
	public ResponseEntity<Associate> addAssociate(@RequestBody @Validated Associate cObj,
			@RequestHeader("Authorization") String authorization);

	@GetMapping(value = "/associate/viewByAssociateId/{associateId}", produces = "application/json")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId,
			@RequestHeader("Authorization") String authorization);

	@PutMapping(value = "/associate/updateAssociate/{associateId}/{associateAddr}", produces = "application/json")
	public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId,
			@PathVariable String associateAddr, @RequestHeader("Authorization") String authorization);

	@GetMapping(value = "/associate/viewAll", produces = "application/json")
	public ResponseEntity<List<Associate>> viewAll(@RequestHeader("Authorization") String authorization);

}
