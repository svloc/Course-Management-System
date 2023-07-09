package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "admissionservice", url = "http://localhost:9093")
public interface AdmissionProxy {
    @DeleteMapping(value = "/admission/deactivate/{courseId}", produces = "application/json")
    public ResponseEntity<Boolean> deactivateAdmission(@PathVariable String courseId,@RequestHeader("Authorization") String authorization);
}
