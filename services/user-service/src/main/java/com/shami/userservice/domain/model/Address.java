package com.shami.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private UUID id;
    private UUID userId;
    private String label;
    private String street;
    private String city;
    private String country;
    private String postalCode;
    private boolean isDefault;
    private LocalDateTime createdAt;
}
