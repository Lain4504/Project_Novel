package com.backend.novelservice.service;

import com.backend.novelservice.dto.request.PromptRequest;
import com.backend.novelservice.dto.response.PromptResponse;
import com.backend.novelservice.mapper.PromptMapper;
import com.backend.novelservice.repository.PromptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromptService {
    PromptRepository promptRepository;
    PromptMapper promptMapper;
    public PromptResponse createPrompt(PromptRequest request){
        return promptMapper.toPromptResponse(promptRepository.save(promptMapper.toPrompt(request)));
    }
    public PromptResponse updatePrompt(String promptId, PromptRequest request){
        var prompt = promptRepository.findById(promptId).orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        promptMapper.updatePromptFromRequest(prompt, request);
        return promptMapper.toPromptResponse(promptRepository.save(prompt));
    }
    public void deletePrompt(String promptId){
        promptRepository.deleteById(promptId);
    }
    public PromptResponse getPrompt(String promptId){
        return promptMapper.toPromptResponse(promptRepository.findById(promptId).orElseThrow(() -> new IllegalArgumentException("Prompt not found")));
    }
    public List<PromptResponse> getAllPrompts(){
        return promptRepository.findAll().stream().map(promptMapper::toPromptResponse).toList();
    }
}
