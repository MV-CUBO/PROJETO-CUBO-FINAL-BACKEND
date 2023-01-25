package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.rest.dto.PatientDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.UpdatePatientDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    public PatientDTO create(PatientDTO patientDTO);
    public PatientDTO getPatientById(UUID id);
    public PepDTO findPepByPatientId(UUID id);
    public UpdatePatientDTO updateById(UUID id, UpdatePatientDTO patientDTO);
    public List<PatientDTO> getAll();
    public Long countPatientByStatus(EStatus value);
    public Long countPatientByGender(Gender value);
    public void deleteById(UUID id);
}
