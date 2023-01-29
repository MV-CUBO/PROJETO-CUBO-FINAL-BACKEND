package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.entity.Address;

import java.util.UUID;

public interface AddressService {
    public Address create(Address address, String message);
    public Address updateAddressById(Address address);
    public void deleteById(UUID id);
}
