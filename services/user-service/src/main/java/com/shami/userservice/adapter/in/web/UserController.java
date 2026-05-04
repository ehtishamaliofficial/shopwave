package com.shami.userservice.adapter.in.web;

import com.shami.common.context.RequestContext;
import com.shami.common.response.ApiResponse;
import com.shami.userservice.application.dto.*;
import com.shami.userservice.application.mapper.AddressMapper;
import com.shami.userservice.application.mapper.UserMapper;
import com.shami.userservice.domain.model.Address;
import com.shami.userservice.domain.model.User;
import com.shami.userservice.domain.port.in.GetCurrentUserUseCase;
import com.shami.userservice.domain.port.in.ManageAddressUseCase;
import com.shami.userservice.domain.port.in.RegisterUserUseCase;
import com.shami.userservice.domain.port.in.UpdateCurrentUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final UpdateCurrentUserUseCase updateCurrentUserUseCase;
    private final ManageAddressUseCase manageAddressUseCase;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final RequestContext requestContext;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
                request.email(), request.password(), request.fullName(), request.phone()
        );

        User user = registerUserUseCase.register(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", userMapper.toResponse(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        User user = getCurrentUserUseCase.getCurrentUser(requestContext.getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.success(userMapper.toResponse(user)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request) {

        UpdateCurrentUserUseCase.UpdateUserCommand command = new UpdateCurrentUserUseCase.UpdateUserCommand(
                request.fullName(), request.phone()
        );

        User user = updateCurrentUserUseCase.update(requestContext.getCurrentUserId(), command);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", userMapper.toResponse(user)));
    }

    @PostMapping("/me/addresses")
    public ResponseEntity<ApiResponse<AddressResponse>> addAddress(
            @Valid @RequestBody AddressRequest request) {

        ManageAddressUseCase.AddAddressCommand command = new ManageAddressUseCase.AddAddressCommand(
                request.label(), request.street(), request.city(),
                request.country(), request.postalCode(), request.isDefault()
        );

        Address address = manageAddressUseCase.addAddress(requestContext.getCurrentUserId(), command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Address added successfully", addressMapper.toResponse(address)));
    }

    @GetMapping("/me/addresses")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses() {
        List<Address> addresses = manageAddressUseCase.getAddresses(requestContext.getCurrentUserId());
        return ResponseEntity.ok(ApiResponse.success(addressMapper.toResponseList(addresses)));
    }
}
