package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SecurityService", url = "http://localhost:9091")
public interface CourseProxy {

}
