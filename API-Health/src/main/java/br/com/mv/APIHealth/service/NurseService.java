package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.entity.Nurse;
import br.com.mv.APIHealth.rest.dto.NurseDTO;

import java.util.List;
import java.util.UUID;

public interface NurseService {
    public NurseDTO create(NurseDTO nurseDTO);
    public NurseDTO getPatientById(UUID id);
    public List<Nurse> getAll();
    public NurseDTO update(UUID id, NurseDTO nurseDTO);
    public void deleteById(UUID id);
}
