package com.shami.userservice.application.service;

import com.shami.userservice.domain.model.Address;
import com.shami.userservice.domain.port.in.ManageAddressUseCase;
import com.shami.userservice.domain.port.out.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService implements ManageAddressUseCase {

    private final AddressRepository addressRepository;

    @Override
    public Address addAddress(UUID userId, AddAddressCommand command) {
        if (command.isDefault()) {
            addressRepository.unsetDefaultAddresses(userId);
        }

        Address address = Address.builder()
                .userId(userId)
                .label(command.label())
                .street(command.street())
                .city(command.city())
                .country(command.country())
                .postalCode(command.postalCode())
                .isDefault(command.isDefault())
                .createdAt(LocalDateTime.now())
                .build();

        return addressRepository.save(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAddresses(UUID userId) {
        return addressRepository.findByUserId(userId);
    }
}
