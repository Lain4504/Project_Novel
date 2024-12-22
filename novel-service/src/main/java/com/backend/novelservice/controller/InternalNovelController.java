package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.novelservice.dto.response.NovelDetailsResponse;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalNovelController {
    NovelService novelService;

    @PostMapping("/internal/novels")
    public List<NovelDetailsResponse> getNovelDetails(@RequestBody List<String> novelIds) {
        return novelService.getNovelDetails(novelIds);
    }
}
