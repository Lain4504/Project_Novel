package com.backend.novel.identityservice.controller;

import com.backend.novel.identityservice.dto.request.PermissionRequest;
import com.backend.novel.identityservice.dto.response.PermissionResponse;
import com.backend.novel.identityservice.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    @PostMapping
    ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ResponseEntity.ok(permissionService.create(request));
    }
    @GetMapping
    ResponseEntity<List<PermissionResponse>> getPermissions() {
        return ResponseEntity.ok(permissionService.getAll());
    }
    @DeleteMapping("/{permissionId}")
    ResponseEntity<Void> deletePermission(@PathVariable("permissionId") String permissionId) {
        permissionService.delete(permissionId);
        return ResponseEntity.ok().build();
    }
}
