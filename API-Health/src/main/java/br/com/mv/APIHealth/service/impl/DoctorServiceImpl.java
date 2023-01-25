package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.DoctorRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        Doctor doctor =  doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("{noExist.id.field}")
        );

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
        List<DoctorDTO> doctorsDTO = new ArrayList<>();

        doctors.forEach(doctor -> {
            DoctorDTO doctorDTO = new DoctorDTO();

            BeanUtils.copyProperties(doctor, doctorDTO);

            doctorsDTO.add(doctorDTO);
        });

        return doctorsDTO;
    }

    @Override
    public DoctorDTO update(UUID id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("{noExist.id.field}")
        );

        doctorDTO.setUpdateAT(LocalDateTime.now());
        doctorDTO.setCreatedAt(doctor.getCreatedAt());
        doctorDTO.setId(doctorDTO.getId());

        BeanUtils.copyProperties(doctor, doctorDTO);
        Doctor updatedDoctor = this.doctorRepository.save(doctor);

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
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "{noExist.id.field}"));
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
            String patientNotFoundMessage = messageSource.getMessage("exist.cpf.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(patientNotFoundMessage);
        }
    }

    private Address createAddressForDoctor(Address addressDto) {
        if (addressDto != null) {
            if (addressDto.getZipCode() == null || addressDto.getStreet() == null || addressDto.getNumber() == null || addressDto.getDistrict() == null || addressDto.getCity() == null || addressDto.getState() == null) {
                throw new BadRequestException("{required.address.field}");
            } else {
                addressDto = new Address(null, addressDto.getZipCode(), addressDto.getStreet(), addressDto.getNumber(), addressDto.getDistrict(), addressDto.getCity(), addressDto.getState(), addressDto.getComplements());
            }
        }

        return this.addressService.create(addressDto, MESSAGE);
    }

    private void validateForUpdateDoctor(DoctorDTO doctorDTO, Doctor doctor) {
        doctor.setName(doctorDTO.getName() != null ? doctorDTO.getName() : doctor.getName());

        doctor.setPhone(doctorDTO.getPhone() != null ? doctorDTO.getPhone() : doctor.getPhone());

        doctor.setStatus(doctorDTO.getStatus() != null ? doctorDTO.getStatus() : doctor.getStatus());

        doctor.setCrm(doctorDTO.getCrm() != null ? doctorDTO.getCrm() : doctor.getCrm());

        doctor.setMaritalStatus(doctorDTO.getMaritalStatus() != null ? doctorDTO.getMaritalStatus() : doctor.getMaritalStatus());

        doctor.setGender(doctorDTO.getGender() != null ? doctorDTO.getGender() : doctor.getGender());

        doctor.setEmail(doctorDTO.getEmail() != null ? doctorDTO.getEmail() : doctor.getEmail());

        doctor.setDateOfBirth(doctorDTO.getDateOfBirth() != null ? doctorDTO.getDateOfBirth() : doctor.getDateOfBirth());

        doctor.setUpdateAT(LocalDateTime.now());
    }

}
