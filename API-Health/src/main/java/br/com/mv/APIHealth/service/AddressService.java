package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.entity.Address;

import java.util.UUID;

public interface AddressService {
    public Address create(Address address, String message);
    public void deleteById(UUID id);
}
