package com.backend.gateway.service;

import com.backend.gateway.dto.request.IntrospectRequest;
import com.backend.gateway.dto.response.IntrospectResponse;
import com.backend.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;
    public Mono<ResponseEntity<IntrospectResponse>> instrospect(String token) {
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
