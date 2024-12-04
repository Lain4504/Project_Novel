package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelCreationRequest;
import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelResponse;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.service.NovelService;
import com.backend.novelservice.service.NovelVolumeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novel-volumes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelVolumeController {
   NovelVolumeService novelVolumeService;
   @PostMapping("/create")
   ApiResponse<NovelVolumeResponse> createNovelVolume(@RequestBody NovelVolumeRequest request) {
       return ApiResponse.<NovelVolumeResponse>builder()
               .result(novelVolumeService.createNovelVolume(request)).build();
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
    @GetMapping("/{id}")
    ApiResponse<NovelVolumeResponse> getNovelVolume(@PathVariable String id) {
        return ApiResponse.<NovelVolumeResponse>builder()
                .result(novelVolumeService.getNovelVolume(id)).build();
    }
    @GetMapping("/get-all/")
    ApiResponse<PageResponse<NovelVolumeResponse>> myPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ApiResponse.<PageResponse<NovelVolumeResponse>>builder()
                .result(novelVolumeService.getNovelVolumes(page, size)).build();
    }

}
