package com.backend.gateway.configuration;

import com.backend.gateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;

    @NonFinal
    private String[] publicEndpoints = {
        "/identity/auth/token",
        "/identity/auth/outbound/authentication",
        "/identity/users/registration"
    }; //can be config .*
    @Value("${app.api-prefix}")
    @NonFinal
    private String apiPrefix;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoint(exchange.getRequest()))
             return chain.filter(exchange);

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader))
            return unauthorized(exchange.getResponse());
        String token = authHeader.getFirst().replace("Bearer ", "");
        return identityService.instrospect(token).flatMap(instrospectReponse -> {
            if (instrospectReponse.getBody().isValid()){
                return chain.filter(exchange);
            }
            return unauthorized(exchange.getResponse());
        }).onErrorResume(throwable -> unauthorized(exchange.getResponse()));
    }
    @Override
    public int getOrder() {
        return -1;
    }
    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints)
                .anyMatch(s -> request.getURI().getPath().matches(apiPrefix + s));
    }
    Mono<Void> unauthorized(ServerHttpResponse response) {
        String body = "{\"message\":\"Unauthorized\"}";
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
