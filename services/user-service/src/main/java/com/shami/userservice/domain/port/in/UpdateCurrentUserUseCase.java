package com.shami.userservice.domain.port.in;

import com.shami.userservice.domain.model.User;

import java.util.UUID;

public interface UpdateCurrentUserUseCase {
    User update(UUID userId, UpdateUserCommand command);

    record UpdateUserCommand(
            String fullName,
            String phone
    ) {}
}
