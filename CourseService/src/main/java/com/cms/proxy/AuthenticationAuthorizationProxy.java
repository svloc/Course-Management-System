package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SecurityService", url = "http://localhost:9098")
public interface AuthenticationAuthorizationProxy {
    @GetMapping("/app/validateToken/{authorization}")
    public boolean isValidToken(@PathVariable String authorization);
    @PostMapping("/app/validateTokenForAdmin/{authorization}")
    public boolean isValidToken(@PathVariable String authorization,@RequestParam("role") String role);
}
