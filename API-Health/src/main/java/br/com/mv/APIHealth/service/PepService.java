package br.com.mv.APIHealth.service;


import br.com.mv.APIHealth.domain.enums.EStatePatient;
import br.com.mv.APIHealth.rest.dto.GetPepDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PepService {
    public GetPepDTO create(PepDTO pepDTO);
    public PepDTO getPepById(UUID id);
    public PepDTO updateById(UUID id, PepDTO pepDTO);
    public List<PepDTO> getAll();
    public void deleteById(UUID id);
    public void updateDatePep(LocalDateTime date);
    public List<PepDTO> getAllByStatus(EStatePatient status);
    public long getNumInStatus(EStatePatient status);
}
