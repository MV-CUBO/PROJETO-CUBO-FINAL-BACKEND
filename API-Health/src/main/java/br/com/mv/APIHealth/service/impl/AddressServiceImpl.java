package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.repository.AddressRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address address, String message) {
        try {
           address = this.addressRepository.save(address);
        } catch (Exception ex) {
            throw new BadRequestException(message);
        }
        return address;
    }

    @Override
    public Address updateAddressById(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public void deleteById(UUID id) {
        this.addressRepository.deleteById(id);
    }
}
