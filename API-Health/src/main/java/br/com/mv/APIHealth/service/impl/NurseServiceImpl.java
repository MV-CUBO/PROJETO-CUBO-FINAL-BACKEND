package br.com.mv.APIHealth.service.impl;


import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.NurseRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
@RequiredArgsConstructor
public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;
    private final AddressService addressService;
    private final String MESSAGE = "Provide the patient's address.";

    @Override
    public NurseDTO create(NurseDTO nurseDTO) {
        this.validateNurseExistByCpf(nurseDTO.getCpf());

        this.stepsForCreationNurse(nurseDTO);

        Nurse nurse = new Nurse();

        BeanUtils.copyProperties(nurseDTO, nurse);

        Nurse newNurse = nurseRepository.save(nurse);

        BeanUtils.copyProperties(newNurse, nurseDTO);
        return nurseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public NurseDTO getNurseById(UUID id) {
        Nurse nurse = nurseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nurse not found."));

        NurseDTO nurseDTO = new NurseDTO();
        BeanUtils.copyProperties(nurse, nurseDTO);
        return nurseDTO;
    }

    @Override
    public List<NurseDTO> getAll() {
        List<Nurse> nurses = nurseRepository.findAll();

        List<NurseDTO> nursesDTO = new ArrayList<>();

        nurses.forEach(nurse -> {
            NurseDTO nurseDTO = new NurseDTO();

            BeanUtils.copyProperties(nurse, nurseDTO);

            nursesDTO.add(nurseDTO);
        });


        return nursesDTO;
    }

    @Override
    public NurseDTO update(UUID id, NurseDTO nurseDTO) {

        Nurse nurse = this.validateNurseExists(id);

        this.validateForUpdateNurse(nurseDTO, nurse);

        nurseDTO.setId(nurseDTO.getId());

        BeanUtils.copyProperties(nurse, nurseDTO);

        Nurse nurseUpdated = this.nurseRepository.save(nurse);

        BeanUtils.copyProperties(nurseUpdated, nurseDTO);
        return nurseDTO;
    }


    @Override
    public void delete(UUID id) {
        nurseRepository.findById(id).map(nurse -> {
            nurseRepository.delete(nurse);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Nurse not found."));
    }

    private NurseDTO stepsForCreationNurse(NurseDTO nurseDTO) {
        Address newAddress = this.createAddressForNurse(nurseDTO.getAddress());

        nurseDTO.setAddress(newAddress);

        nurseDTO.setCreatedAt(LocalDateTime.now());
        nurseDTO.setUpdateAT(LocalDateTime.now());

        nurseDTO.setStatus(EStatus.ACTIVATE);

        return nurseDTO;
    }

    private Nurse validateNurseExists(UUID id) {
        Nurse nurse = this.nurseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found."));

        return nurse;
    }

    private void validateNurseExistByCpf(String cpf) {
        Boolean nurseIsPresent = this.nurseRepository.findByCpf(cpf).isPresent();

        if (nurseIsPresent) throw new BadRequestException("CPF already registered in the database!");
    }

    private Address createAddressForNurse(Address addressDto) {
        if (addressDto != null) {
            if (addressDto.getZipCode() == null
                    || addressDto.getStreet() == null
                    || addressDto.getNumber() == null
                    || addressDto.getDistrict() == null
                    || addressDto.getCity() == null
                    || addressDto.getState() == null) {
                throw new BadRequestException("All address fields must be filled in!");
            } else {
                addressDto = new Address(null,
                        addressDto.getZipCode(),
                        addressDto.getStreet(),
                        addressDto.getNumber(),
                        addressDto.getDistrict(),
                        addressDto.getCity(),
                        addressDto.getState(),
                        addressDto.getComplements());
            }
        }

        return this.addressService.create(addressDto, MESSAGE);
    }

    private void validateForUpdateNurse(NurseDTO nurseDTO, Nurse nurse) {
        nurse.setName(nurseDTO.getName() != null ? nurseDTO.getName() : nurse.getName());

        nurse.setPhone(nurseDTO.getPhone() != null ? nurseDTO.getPhone() : nurse.getPhone());

        nurse.setStatus(nurseDTO.getStatus() != null ? nurseDTO.getStatus() : nurse.getStatus());

        nurse.setCoren(nurseDTO.getCoren() != null ? nurseDTO.getCoren() : nurse.getCoren());

        nurse.setMaritalStatus(nurseDTO.getMaritalStatus() != null ? nurseDTO.getMaritalStatus() : nurse.getMaritalStatus());

        nurse.setGender(nurseDTO.getGender() != null ? nurseDTO.getGender() : nurse.getGender());

        nurse.setEmail(nurseDTO.getEmail() != null ? nurseDTO.getEmail() : nurse.getEmail());

        nurse.setEmail(nurseDTO.getEmail() != null ? nurseDTO.getEmail() : nurse.getEmail());

        nurse.setDateOfBirth(nurseDTO.getDateOfBirth() != null ? nurseDTO.getDateOfBirth() : nurse.getDateOfBirth());

        nurse.setUpdateAT(LocalDateTime.now());
    }

}
