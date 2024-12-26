package com.backend.novelservice.controller;

import com.backend.novelservice.dto.response.NovelDetailsResponse;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalNovelController {
    NovelService novelService;

    @PostMapping("/internal/novels")
    public List<NovelDetailsResponse> getNovelDetails(@RequestBody List<String> novelIds) {
        return novelService.getNovelDetails(novelIds);
    }

    @PutMapping(value = "/internal/novels/rating-update/{novelId}")
    public void updateNovelRating(@PathVariable String novelId, @RequestBody long rating) {
        novelService.updateNovelRating(novelId, rating);
    }

    @PutMapping(value = "/internal/novels/follow-update/{novelId}")
    public void updateNovelFollow(@PathVariable String novelId, @RequestBody boolean isNewFollow) {
        novelService.updateNovelFollow(novelId, isNewFollow);
    }

    @PutMapping(value = "/internal/novels/rating-update-with-same-user/{novelId}")
    public void updateNovelRatingWithSameUser(@PathVariable String novelId, @RequestBody Map<String, Long> ratings) {
        long newRating = ratings.get("newRating");
        long oldRating = ratings.get("oldRating");
        novelService.updateNovelRatingWithSameUser(novelId, newRating, oldRating);
    }
}
