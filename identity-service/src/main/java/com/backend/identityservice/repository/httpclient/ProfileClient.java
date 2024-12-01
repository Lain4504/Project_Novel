package com.backend.identityservice.repository.httpclient;

import com.backend.identityservice.configuration.AuthenticationRequestInterceptor;
import com.backend.identityservice.dto.request.ProfileCreationRequest;
import com.backend.identityservice.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
