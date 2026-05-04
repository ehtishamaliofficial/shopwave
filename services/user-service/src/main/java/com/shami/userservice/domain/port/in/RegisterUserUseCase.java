package com.shami.userservice.domain.port.in;

import com.shami.userservice.domain.model.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);

    record RegisterUserCommand(
            String email,
            String password,
            String fullName,
            String phone
    ) {}
}
