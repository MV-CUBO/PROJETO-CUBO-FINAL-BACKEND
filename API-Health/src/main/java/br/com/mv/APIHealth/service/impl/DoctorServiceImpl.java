package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.DoctorRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final AddressService addressService;

    private final String MESSAGE = "Provide the patient's address.";

    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        this.validateDoctorExistByCpf(doctorDTO.getCpf());

        this.stepsForCreationDoctor(doctorDTO);

        Doctor doctor = new Doctor();

        BeanUtils.copyProperties(doctorDTO, doctor);

        Doctor newDoctor = doctorRepository.save(doctor);

        BeanUtils.copyProperties(newDoctor, doctorDTO);
        return doctorDTO;
    }
    @Override
    public DoctorDTO getDoctorById(UUID id) {
        Doctor doctor =  doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found.")
        );

        DoctorDTO dto = new DoctorDTO();
        BeanUtils.copyProperties(doctor, dto);
        return dto;
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll() ;
    }

    @Override
    public DoctorDTO update(UUID id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        doctorDTO.setUpdateAT(LocalDateTime.now());
        doctorDTO.setCreatedAt(doctor.getCreatedAt());
        doctorDTO.setId(doctorDTO.getId());
        BeanUtils.copyProperties(doctor, doctorDTO);
        Doctor updatedDoctor = doctorRepository.save(doctor);

        BeanUtils.copyProperties(updatedDoctor, doctorDTO);
        return doctorDTO;
    }

    @Override
    public void delete(UUID id) {
        doctorRepository.findById(id)
                .map(doctor ->
                {
                    doctorRepository.delete(doctor);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor not found."));
    }

    private DoctorDTO stepsForCreationDoctor(DoctorDTO doctorDTO) {
        Address newAddress = this.createAddressForDoctor(doctorDTO.getAddress());

        doctorDTO.setAddress(newAddress);

        doctorDTO.setCreatedAt(LocalDateTime.now());
        doctorDTO.setUpdateAT(LocalDateTime.now());

        doctorDTO.setStatus(EStatus.ACTIVATE);

        return doctorDTO;
    }

    private void validateDoctorExistByCpf(String cpf) {
        Boolean doctorIsPresent = this.doctorRepository.findByCpf(cpf).isPresent();

        if (doctorIsPresent) throw new BadRequestException("CPF already registered in the database!");
    }

    private Address createAddressForDoctor(Address addressDto) {
        if (addressDto != null) {
            if (addressDto.getZipCode() == null || addressDto.getStreet() == null || addressDto.getNumber() == null || addressDto.getDistrict() == null || addressDto.getCity() == null || addressDto.getState() == null) {
                throw new BadRequestException("All address fields must be filled in!");
            } else {
                addressDto = new Address(null, addressDto.getZipCode(), addressDto.getStreet(), addressDto.getNumber(), addressDto.getDistrict(), addressDto.getCity(), addressDto.getState(), addressDto.getComplements());
            }
        }

        return this.addressService.create(addressDto, MESSAGE);
    }

}
