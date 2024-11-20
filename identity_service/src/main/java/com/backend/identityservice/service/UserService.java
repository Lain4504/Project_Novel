package com.backend.identityservice.service;

import com.backend.identityservice.enums.UserState;
import com.backend.identityservice.constant.PredefinedRole;
import com.backend.identityservice.dto.request.UserCreationRequest;
import com.backend.identityservice.dto.request.UserUpdateRequest;
import com.backend.identityservice.dto.response.UserResponse;
import com.backend.identityservice.entity.Role;
import com.backend.identityservice.entity.User;
import com.backend.event.NotificationEvent;
import com.backend.identityservice.mapper.ProfileMapper;
import com.backend.identityservice.mapper.UserMapper;
import com.backend.identityservice.repository.RoleRepository;
import com.backend.identityservice.repository.UserRepository;
import com.backend.identityservice.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ProfileClient profileClient;
    ProfileMapper profileMapper;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new RuntimeException("User already exists");
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        user.setState(UserState.INACTIVE);
        user.setCreatedDate(java.time.LocalDate.now());
        user = userRepository.save(user);
        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());
        profileClient.createProfile(profileRequest);
        NotificationEvent event = NotificationEvent
                .builder()
                .channel("EMAIL")
                .recipient(user.getEmail())
                .subject("Account Activation")
                .body("Please activate your account")
                .build();
        //Publish message to kafka
        kafkaTemplate.send("onboard-successfull", event);

        return userMapper.toUserResponse(user);
    }
    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MessageDescriptorFormatException("User not found"));
        userMapper.updateUser(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new MessageDescriptorFormatException("User not found"));
        return userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new MessageDescriptorFormatException("User not found")));
    }

}
