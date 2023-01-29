package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.NurseDTO;
import br.com.mv.APIHealth.rest.dto.UpdateNurseDTO;

import java.util.List;
import java.util.UUID;

public interface NurseService {
    public NurseDTO create(NurseDTO nurseDTO);
    public NurseDTO getNurseById(UUID id);
    public List<NurseDTO> getAll();
    UpdateNurseDTO update(UUID id, UpdateNurseDTO nurseDTO);
    public void delete(UUID id);
}
