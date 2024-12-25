package com.backend.identityservice.mapper;

import com.backend.identityservice.dto.request.ProfileCreationRequest;
import com.backend.identityservice.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
