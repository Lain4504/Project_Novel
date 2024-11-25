package com.backend.novelservice.controller;

import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelController {
    NovelService novelService;

    @PostMapping("/create")
    ResponseEntity<NovelResponse> createNovel(@RequestBody NovelCreationRequest request) {
        return ResponseEntity.ok(novelService.createNovel(request));
    }
    @PutMapping("/update/{novelId}")
    ResponseEntity<NovelResponse> updateNovel(@PathVariable("novelId") String novelId, @RequestBody NovelCreationRequest request) {
        return ResponseEntity.ok(novelService.updateNovel(novelId, request));
    }
    @DeleteMapping("/delete/{novelId}")
    ResponseEntity<Void> deleteNovel(@PathVariable("novelId") String novelId) {
        novelService.deleteNovel(novelId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{novelId}")
    ResponseEntity<NovelResponse> getNovel(@PathVariable("novelId") String novelId) {
        return ResponseEntity.ok(novelService.getNovel(novelId));
    }
    @GetMapping
    ResponseEntity<List<NovelResponse>> getNovels() {
        return ResponseEntity.ok(novelService.getNovels());
    }
}
