package com.shami.userservice.domain.port.out;

import com.shami.userservice.domain.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressRepository {
    Address save(Address address);
    List<Address> findByUserId(UUID userId);
    void unsetDefaultAddresses(UUID userId);
}
