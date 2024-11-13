package com.backend.novel.identityservice.mapper;

import com.backend.novel.identityservice.dto.request.RoleRequest;
import com.backend.novel.identityservice.dto.response.RoleResponse;
import com.backend.novel.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
