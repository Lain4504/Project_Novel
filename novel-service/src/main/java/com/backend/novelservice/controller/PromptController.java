package com.backend.novelservice.controller;

import com.backend.dto.response.ApiResponse;
import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.PromptRequest;
import com.backend.novelservice.dto.response.PromptResponse;
import com.backend.novelservice.service.PromptService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/prompt")
public class PromptController {
    PromptService promptService;
    @PostMapping("/create")
    public ApiResponse<PromptResponse> createPrompt(@RequestBody PromptRequest request){
        return ApiResponse.<PromptResponse>builder().result(promptService.createPrompt(request)).build();
    }
    @PutMapping("/update/{promptId}")
    public ApiResponse<PromptResponse> updatePrompt(@PathVariable String promptId, @RequestBody PromptRequest request){
        return ApiResponse.<PromptResponse>builder().result(promptService.updatePrompt(promptId, request)).build();
    }
    @DeleteMapping("/delete/{promptId}")
    public ApiResponse<Void> deletePrompt(@PathVariable String promptId){
        promptService.deletePrompt(promptId);
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping("/get/{promptId}")
    public ApiResponse<PromptResponse> getPrompt(@PathVariable String promptId){
        return ApiResponse.<PromptResponse>builder().result(promptService.getPrompt(promptId)).build();
    }
    @GetMapping("/get/all")
    public ApiResponse<PageResponse<PromptResponse>> getAllPrompts(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ){
        return ApiResponse.<PageResponse<PromptResponse>>builder().result(promptService.getAllPrompts(page, size)).build();
    }
}
