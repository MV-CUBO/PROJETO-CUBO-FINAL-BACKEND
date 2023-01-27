package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.NurseDTO;

import java.util.List;
import java.util.UUID;

public interface NurseService {
    public NurseDTO create(NurseDTO nurseDTO);
    public NurseDTO getNurseById(UUID id);
    public List<NurseDTO> getAll();

    NurseDTO update(UUID id, NurseDTO nurseDTO);

    public void delete(UUID id);
}
