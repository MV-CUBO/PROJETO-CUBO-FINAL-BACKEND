package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.DoctorRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.rest.dto.UpdateDoctorDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final AddressService addressService;

    private final MessageSource messageSource;

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
    @Transactional(readOnly = true)
    public DoctorDTO getDoctorById(UUID id) {

        Doctor doctor = this.validateDoctorExists(id);


        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctor, doctorDTO);
        return doctorDTO;
    }

    @Override
    public Long countDoctorByStatus(EStatus value) {
        return this.doctorRepository.countDoctorByStatus(value);
    }

    @Override
    public List<DoctorDTO> getAll() {

        List<Doctor> doctors = doctorRepository.findAll();

        if (doctors.isEmpty()) {
            String doctorNotFoundMessage = messageSource.getMessage("noExist.doctor.database", null, Locale.getDefault());
            throw new ResourceNotFoundException(doctorNotFoundMessage);
        }

        List<DoctorDTO> doctorsDTO = new ArrayList<>();

        doctors.forEach(nurse -> {
            DoctorDTO doctorDTO = new DoctorDTO();

            BeanUtils.copyProperties(nurse, doctorDTO);

            doctorsDTO.add(doctorDTO);
        });

        return doctorsDTO;
    }

    @Override
    public UpdateDoctorDTO update(UUID id, UpdateDoctorDTO updateDto) {

        Doctor doctor = this.validateDoctorExists(id);

        this.validateForUpdateDoctor(updateDto, doctor);


        updateDto.setId(updateDto.getId());

        BeanUtils.copyProperties(doctor, updateDto);

        Doctor doctorUpdated = this.doctorRepository.save(doctor);

        BeanUtils.copyProperties(doctorUpdated, updateDto);
        return updateDto;
    }

    @Override
    public void delete(UUID id) {
        Doctor doctor = this.validateDoctorExists(id);

        this.addressService.deleteById(doctor.getAddress().getId());
        this.doctorRepository.deleteById(id);
    }

    private Doctor validateDoctorExists(UUID id) {
        Optional<Doctor> doctor = this.doctorRepository.findById(id);

        if (doctor.isEmpty()) {
            String patientNotFoundMessage = messageSource.getMessage("noExist.id.fields", null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }

        return doctor.get();
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
        boolean doctorIsPresent = this.doctorRepository.findByCpf(cpf).isPresent();

        if (doctorIsPresent) {

            String patientNotFoundMessage = messageSource.getMessage("Exist.cpf.field",

                    null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }
    }

    private Address createAddressForDoctor(Address addressDto) {
        if (addressDto != null) {
            if (addressDto.getZipCode() == null || addressDto.getStreet() == null || addressDto.getNumber() == null || addressDto.getDistrict() == null || addressDto.getCity() == null || addressDto.getState() == null) {
                String addressValidationFields = messageSource.getMessage("required.address.field", null, Locale.getDefault());
                throw new BadRequestException(addressValidationFields);
            } else {
                addressDto = new Address(null, addressDto.getZipCode(), addressDto.getStreet(), addressDto.getNumber(), addressDto.getDistrict(), addressDto.getCity(), addressDto.getState(), addressDto.getComplements());
            }
        }

        return this.addressService.create(addressDto, MESSAGE);
    }

    private void validateForUpdateDoctor(UpdateDoctorDTO doctorDTO, Doctor doctor) {
        doctor.setName(doctorDTO.getName() != null ? doctorDTO.getName() : doctor.getName());

        doctor.setPhone(doctorDTO.getPhone() != null ? doctorDTO.getPhone() : doctor.getPhone());

        doctor.setStatus(doctorDTO.getStatus() != null ? doctorDTO.getStatus() : doctor.getStatus());

        doctor.setCrm(doctorDTO.getCrm() != null ? doctorDTO.getCrm() : doctor.getCrm());

        doctor.setMaritalStatus(doctorDTO.getMaritalStatus() != null ? doctorDTO.getMaritalStatus() : doctor.getMaritalStatus());

        doctor.setGender(doctorDTO.getGender() != null ? doctorDTO.getGender() : doctor.getGender());

        doctor.setEmail(doctorDTO.getEmail() != null ? doctorDTO.getEmail() : doctor.getEmail());

        doctor.setDateOfBirth(doctorDTO.getDateOfBirth() != null ? doctorDTO.getDateOfBirth() : doctor.getDateOfBirth());

        if (doctorDTO.getAddress() != null && doctor.getAddress() != null) {
            this.forUpdateAddressDoctor(doctorDTO.getAddress(), doctor.getAddress());
        }

        doctor.setUpdateAT(LocalDateTime.now());
    }

    private void forUpdateAddressDoctor(Address addressDto, Address addressEntity) {

        addressEntity.setZipCode(addressDto.getZipCode() != null ? addressDto.getZipCode() : addressEntity.getZipCode());

        addressEntity.setCity(addressDto.getCity() != null ? addressDto.getCity() : addressEntity.getCity());

        addressEntity.setNumber(addressDto.getNumber() != null ? addressDto.getNumber() : addressEntity.getNumber());

        addressEntity.setComplements(addressDto.getComplements()!= null ? addressDto.getComplements() : addressEntity.getComplements());

        addressEntity.setStreet(addressDto.getStreet() != null ? addressDto.getStreet() : addressEntity.getStreet());

        addressEntity.setState(addressDto.getState() != null ? addressDto.getState() : addressEntity.getState());

        addressEntity.setDistrict(addressDto.getDistrict() != null ? addressDto.getDistrict() : addressEntity.getDistrict());

        this.addressService.updateAddressById(addressEntity);
    }
}
