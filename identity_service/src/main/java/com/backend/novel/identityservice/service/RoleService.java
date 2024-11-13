package com.backend.novel.identityservice.service;

import com.backend.novel.identityservice.dto.request.RoleRequest;
import com.backend.novel.identityservice.dto.response.RoleResponse;
import com.backend.novel.identityservice.mapper.PermissionMapper;
import com.backend.novel.identityservice.mapper.RoleMapper;
import com.backend.novel.identityservice.repository.PermissionRepository;
import com.backend.novel.identityservice.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
