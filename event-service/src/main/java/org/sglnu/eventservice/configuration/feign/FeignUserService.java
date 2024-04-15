package org.sglnu.eventservice.configuration.feign;

import org.sglnu.userservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface FeignUserService {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable Long id);

}
