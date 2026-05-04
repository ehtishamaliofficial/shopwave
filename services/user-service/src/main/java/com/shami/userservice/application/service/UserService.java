package com.shami.userservice.application.service;

import com.shami.common.exception.DuplicateResourceException;
import com.shami.common.exception.ResourceNotFoundException;
import com.shami.userservice.domain.model.Role;
import com.shami.userservice.domain.model.User;
import com.shami.userservice.domain.port.in.GetCurrentUserUseCase;
import com.shami.userservice.domain.port.in.RegisterUserUseCase;
import com.shami.userservice.domain.port.in.UpdateCurrentUserUseCase;
import com.shami.userservice.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements RegisterUserUseCase, GetCurrentUserUseCase, UpdateCurrentUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new DuplicateResourceException("Email already registered: " + command.email());
        }

        User user = User.builder()
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .fullName(command.fullName())
                .phone(command.phone())
                .role(Role.CUSTOMER)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Override
    public User update(UUID userId, UpdateUserCommand command) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.setFullName(command.fullName());
        user.setPhone(command.phone());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.update(user);
    }
}
