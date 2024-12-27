package com.backend.novelservice.mapper;

import com.backend.novelservice.dto.request.PromptRequest;
import com.backend.novelservice.dto.response.PromptResponse;
import com.backend.novelservice.entity.Prompt;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromptMapper {
    Prompt toPrompt(PromptRequest promptRequest);

    PromptResponse toPromptResponse(Prompt prompt);

    void updatePromptFromRequest(@MappingTarget Prompt prompt, PromptRequest promptRequest);
}
