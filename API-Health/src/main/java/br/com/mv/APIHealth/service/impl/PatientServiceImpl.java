package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.PatientRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.rest.dto.UpdatePatientDTO;
import br.com.mv.APIHealth.service.AddressService;
import br.com.mv.APIHealth.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    private AddressService addressService;

    public PatientServiceImpl(PatientRepository patientRepository, AddressService addressService) {
        this.patientRepository = patientRepository;
        this.addressService = addressService;
    }

    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        this.validatePatientExistByCpf(patientDTO.getCpf());

        this.stepsForCreationPatient(patientDTO);

        Patient patient = new Patient();

        BeanUtils.copyProperties(patientDTO, patient);

        Patient newPatient = this.patientRepository.save(patient);

        BeanUtils.copyProperties(newPatient, patientDTO);

        return patientDTO;
    }

    @Override
    public PatientDTO getPatientById(UUID id) {
        Patient patient = this.validatePatientExists(id);

        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);

        return patientDTO;
    }

    @Override
    public UpdatePatientDTO updateById(UUID id, UpdatePatientDTO patientDTO) {
        Patient patient = this.validatePatientExists(id);

        this.validateForUpdatePatient(patientDTO, patient);

        patientDTO.setId(patientDTO.getId());

        BeanUtils.copyProperties(patient, patientDTO);

        Patient patientUpdated = this.patientRepository.save(patient);

        BeanUtils.copyProperties(patientUpdated, patientDTO);
        return patientDTO;
    }

    @Override
    public List<PatientDTO> getAll() {
        List<Patient> patients= this.patientRepository.findAll();

        List<PatientDTO> patientsDTO = new ArrayList();

        patients.forEach(patient -> {
            PatientDTO patientDTO = new PatientDTO();

            BeanUtils.copyProperties(patient, patientDTO);

            patientsDTO.add(patientDTO);
        });

        return patientsDTO;
    }

    @Override
    public void deleteById(UUID id) {
        this.validatePatientExists(id);

        this.patientRepository.deleteById(id);
    }

    private PatientDTO stepsForCreationPatient(PatientDTO patientDTO) {
        Address newAddress = this.createAddressForPatient(patientDTO.getAddress());

        patientDTO.setAddress(newAddress);

        patientDTO.setCreatedAt(LocalDateTime.now());
        patientDTO.setUpdateAT(LocalDateTime.now());

        patientDTO.setStatus(EStatus.ACTIVATE);

        return patientDTO;
    }

    private Patient validatePatientExists (UUID id) {
        Patient patient = this.patientRepository
                    .findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found."));

        return patient;
    }

    private void validateForUpdatePatient(UpdatePatientDTO patientDTO, Patient patient) {
        patient.setName(patientDTO.getName() != null ? patientDTO.getName() : patient.getName());

        patient.setPhone(patientDTO.getPhone() != null ? patientDTO.getPhone() : patient.getPhone());

        patient.setStatus(patientDTO.getStatus() != null ? patientDTO.getStatus() : patient.getStatus());

        patient.setObservation(patientDTO.getObservation() != null ? patientDTO.getObservation() : patient.getObservation());

        patient.setMaritalStatus(patientDTO.getMaritalStatus() != null ? patientDTO.getMaritalStatus() : patient.getMaritalStatus());

        patient.setGender(patientDTO.getGender() != null ? patientDTO.getGender() : patient.getGender());

        patient.setEmail(patientDTO.getEmail() != null ? patientDTO.getEmail() : patient.getEmail());

        patient.setInsuranceCompany(patientDTO.getInsuranceCompany() != null ? patientDTO.getInsuranceCompany() : patient.getInsuranceCompany());

        patient.setDateOfBirth(patientDTO.getDateOfBirth() != null ? patientDTO.getDateOfBirth() : patient.getDateOfBirth());

        patient.setUpdateAT(LocalDateTime.now());
    }

    private Address createAddressForPatient (Address addressDto) {
        if (
                addressDto.getZipCode() == null ||
                addressDto.getStreet() == null ||
                addressDto.getNumber() == null ||
                addressDto.getDistrict() == null ||
                addressDto.getCity() == null ||
                addressDto.getState() == null
        ) {
            throw new BadRequestException("All address fields must be filled in!");
        }

        Address address =  new Address(
                null,
               addressDto.getZipCode(),
               addressDto.getStreet(),
               addressDto.getNumber(),
               addressDto.getDistrict(),
               addressDto.getCity(),
               addressDto.getState(),
               addressDto.getComplements()
        );

        return this.addressService.create(address);
    }

    private void validatePatientExistByCpf(String cpf) {
        Boolean patientIsPresent = this.patientRepository.findByCpf(cpf).isPresent();

        if(patientIsPresent) throw new BadRequestException("CPF already registered in the database!");
    }
}
