package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.repository.PatientRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
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
        this.validationForCreatePatient(patientDTO);

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

    private PatientDTO validationForCreatePatient(PatientDTO patientDTO) {
        Address address = new Address(
                null,
                patientDTO.getAddress().getZipCode(),
                patientDTO.getAddress().getStreet(),
                patientDTO.getAddress().getNumber(),
                patientDTO.getAddress().getDistrict(),
                patientDTO.getAddress().getCity(),
                patientDTO.getAddress().getState(),
                patientDTO.getAddress().getComplements()
        );

        Address newAddress = this.addressService.create(address);

        patientDTO.setAddress(newAddress);

        patientDTO.setCreatedAt(LocalDateTime.now());
        patientDTO.setUpdateAT(LocalDateTime.now());

        patientDTO.setStatus(EStatus.ACTIVATE);

        return patientDTO;
    }

    private Patient validatePatientExists (UUID id) {
        Patient patient = this.patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado."));
        return patient;
    }

    private void validateForUpdatePatient(UpdatePatientDTO patientDTO, Patient patient) {
        if(patientDTO.getName() != null) {
            patient.setName(patientDTO.getName());
        }

        if(patientDTO.getPhone() != null) {
            patient.setPhone(patientDTO.getPhone());
        }

        if(patientDTO.getStatus() != null) {
            patient.setName(patientDTO.getName());
        }

        if(patientDTO.getObservation() != null) {
            patient.setObservation(patientDTO.getObservation());
        }

        if(patientDTO.getMaritalStatus() != null) {
            patient.setMaritalStatus(patientDTO.getMaritalStatus());
        }

        if(patientDTO.getGender() != null) {
            patient.setGender(patientDTO.getGender());
        }

        if(patientDTO.getEmail() != null) {
            patient.setEmail(patientDTO.getEmail());
        }

        if(patientDTO.getInsuranceCompany() != null) {
            patient.setEmail(patientDTO.getEmail());
        }

        if(patientDTO.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDTO.getDateOfBirth());
        }

        patient.setUpdateAT(LocalDateTime.now());
    }
}
