package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.PatientDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    public PatientDTO create(PatientDTO patientDTO);
    public PatientDTO getPatientById(UUID id);
    public PatientDTO updateById(UUID id, PatientDTO patientDTO);
    public List<PatientDTO> getAll();
    public void deleteById(UUID id);
}
