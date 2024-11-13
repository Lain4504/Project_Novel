package com.backend.novel.identityservice.mapper;
import com.backend.novel.identityservice.dto.request.PermissionRequest;
import com.backend.novel.identityservice.dto.response.PermissionResponse;
import com.backend.novel.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
