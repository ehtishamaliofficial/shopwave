package com.shami.userservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank
        String label,
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        String country,
        String postalCode,
        boolean isDefault
) {}
