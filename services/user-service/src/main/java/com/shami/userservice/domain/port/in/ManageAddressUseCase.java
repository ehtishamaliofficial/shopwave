package com.shami.userservice.domain.port.in;

import com.shami.userservice.domain.model.Address;

import java.util.List;
import java.util.UUID;

public interface ManageAddressUseCase {
    Address addAddress(UUID userId, AddAddressCommand command);
    List<Address> getAddresses(UUID userId);

    record AddAddressCommand(
            String label,
            String street,
            String city,
            String country,
            String postalCode,
            boolean isDefault
    ) {}
}
