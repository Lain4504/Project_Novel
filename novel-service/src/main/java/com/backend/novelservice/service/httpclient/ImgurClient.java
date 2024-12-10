package com.backend.novelservice.service.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "imgurClient", url = "https://api.imgur.com/3")
public interface ImgurClient {
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImgurResponse uploadImage(@RequestHeader("Authorization") String authorization, @RequestPart("image") MultipartFile image);
}