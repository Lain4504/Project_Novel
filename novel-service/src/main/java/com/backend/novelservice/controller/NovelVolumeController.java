package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.service.NovelVolumeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel-volumes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelVolumeController {
    NovelVolumeService novelVolumeService;

    @PostMapping("/create/{novelId}")
    ApiResponse<NovelVolumeResponse> createNovelVolume(@PathVariable String novelId, @RequestBody NovelVolumeRequest request) {
        return ApiResponse.<NovelVolumeResponse>builder()
                .result(novelVolumeService.createNovelVolume(novelId, request)).build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<NovelVolumeResponse> updateNovelVolume(@PathVariable String id, @RequestBody NovelVolumeRequest request) {
        return ApiResponse.<NovelVolumeResponse>builder()
                .result(novelVolumeService.updateNovelVolume(id, request)).build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteNovelVolume(@PathVariable String id) {
        novelVolumeService.deleteNovelVolume(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/get/{id}")
    ApiResponse<NovelVolumeResponse> getNovelVolume(@PathVariable String id) {
        return ApiResponse.<NovelVolumeResponse>builder()
                .result(novelVolumeService.getNovelVolume(id)).build();
    }

    @GetMapping("/get/get-all/")
    ApiResponse<List<NovelVolumeResponse>> getNovelVolume() {
        return ApiResponse.<List<NovelVolumeResponse>>builder()
                .result(novelVolumeService.getNovelVolumes()).build();

    }

    @GetMapping("/get/{novelId}/volumes")
    ApiResponse<List<NovelVolumeResponse>> getNovelVolumes(@PathVariable("novelId") String novelId) {
        return ApiResponse.<List<NovelVolumeResponse>>builder()
                .result(novelVolumeService.getVolumesByNovelId(novelId)).build();
    }
    @PutMapping("/reorder/{novelId}")
    ApiResponse<Void> reorderNovelVolumes(@PathVariable String novelId, @RequestBody List<String> volumeIds) {
        novelVolumeService.reorderNovelVolumes(novelId, volumeIds);
        return ApiResponse.<Void>builder().build();
    }
}
