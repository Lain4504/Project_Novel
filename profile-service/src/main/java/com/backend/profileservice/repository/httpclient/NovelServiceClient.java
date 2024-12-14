package com.backend.profileservice.repository.httpclient;

import com.backend.profileservice.configuration.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "novel-service", url = "${app.services.novel}", configuration = {AuthenticationRequestInterceptor.class})
public interface NovelServiceClient {
    @PostMapping(value = "/internal/novels", produces = MediaType.APPLICATION_JSON_VALUE)
    List<NovelDetailsResponse> getNovelDetails(@RequestBody List<String> novelIds);
}

