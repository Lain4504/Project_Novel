package com.backend.searchservice.repository.httpclient;

import com.backend.searchservice.configuration.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "novel-service", url = "${app.services.novel}", configuration = {AuthenticationRequestInterceptor.class})
public interface NovelSenderEvent {
    @GetMapping(value = "/internal/novels/sender-event")
    void sendEvent();
}
