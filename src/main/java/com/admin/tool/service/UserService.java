package com.admin.tool.service;

import com.admin.tool.dto.UserRequest;
import com.admin.tool.dto.UserResponse;
import com.admin.tool.entity.User;
import com.admin.tool.exception.CustomException;
import com.admin.tool.exception.ResourceNotFoundException;
import com.admin.tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponse::from);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .role(request.getRole())
                .enabled(request.getEnabled())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Check if username is being changed and if it's already taken
        if (!user.getUsername().equals(request.getUsername()) &&
                userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("Username already exists");
        }

        // Check if email is being changed and if it's already taken
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already exists");
        }

        user.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setEnabled(request.getEnabled());

        User updatedUser = userRepository.save(user);
        return UserResponse.from(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }
}
