package com.shami.userservice.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressResponse(
        UUID id,
        String label,
        String street,
        String city,
        String country,
        String postalCode,
        boolean isDefault,
        LocalDateTime createdAt
) {}
