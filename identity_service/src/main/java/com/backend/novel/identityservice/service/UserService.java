package com.backend.novel.identityservice.service;

import com.backend.novel.enums.UserState;
import com.backend.novel.identityservice.constant.PredefinedRole;
import com.backend.novel.identityservice.dto.request.UserCreationRequest;
import com.backend.novel.identityservice.dto.request.UserUpdateRequest;
import com.backend.novel.identityservice.dto.response.UserResponse;
import com.backend.novel.identityservice.entity.Role;
import com.backend.novel.identityservice.entity.User;
import com.backend.novel.identityservice.mapper.UserMapper;
import com.backend.novel.identityservice.repository.RoleRepository;
import com.backend.novel.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new MessageDescriptorFormatException("User already exists");
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        user.setState(UserState.INACTIVE);
        user.setCreatedDate(java.time.LocalDate.now());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MessageDescriptorFormatException("User not found"));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new MessageDescriptorFormatException("User not found")));
    }
}
