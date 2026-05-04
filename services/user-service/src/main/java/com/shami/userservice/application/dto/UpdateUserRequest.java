package com.shami.userservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank
        String fullName,
        String phone
) {}
