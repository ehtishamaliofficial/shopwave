package com.shami.userservice.application.mapper;

import com.shami.userservice.application.dto.AddressResponse;
import com.shami.userservice.domain.model.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public AddressResponse toResponse(Address address) {
        if (address == null) return null;
        return new AddressResponse(
                address.getId(),
                address.getLabel(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                address.isDefault(),
                address.getCreatedAt()
        );
    }

    public List<AddressResponse> toResponseList(List<Address> addresses) {
        return addresses.stream().map(this::toResponse).toList();
    }
}
