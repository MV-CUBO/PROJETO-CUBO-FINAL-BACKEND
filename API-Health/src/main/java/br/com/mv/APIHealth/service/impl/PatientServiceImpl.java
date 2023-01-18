package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.repository.PatientRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);

        Patient newPatient = this.patientRepository.save(patient);

        BeanUtils.copyProperties(newPatient, patientDTO);

        return patientDTO;
    }

    @Override
    public PatientDTO getPatientById(UUID id) {
        Patient patient = this.validateExistPatient(id);

        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);

        return patientDTO;
    }

    @Override
    public PatientDTO updateById(UUID id, PatientDTO patientDTO) {
        Patient patient =  this.validateExistPatient(id);

        this.validateForUpdatePatient(patientDTO, patient);

        patientDTO.setId(patientDTO.getId());

        BeanUtils.copyProperties(patientDTO, patient);
        Patient patientUpdated = this.patientRepository.save(patient);

        BeanUtils.copyProperties(patientUpdated, patientDTO);
        return patientDTO;
    }

    @Override
    public List<PatientDTO> getAll() {
         return null;
    }

    @Override
    public void deleteById(UUID id) {
        this.validateExistPatient(id);

        this.patientRepository.deleteById(id);
    }

    private Patient validateExistPatient (UUID patientId) {
        Patient patient = this.patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado."));

        return patient;
    }

    private void validateForUpdatePatient(PatientDTO patientDTO, Patient patient) {
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
