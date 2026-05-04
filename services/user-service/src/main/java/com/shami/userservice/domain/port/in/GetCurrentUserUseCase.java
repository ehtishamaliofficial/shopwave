package com.shami.userservice.domain.port.in;

import com.shami.userservice.domain.model.User;

import java.util.UUID;

public interface GetCurrentUserUseCase {
    User getCurrentUser(UUID userId);
}
