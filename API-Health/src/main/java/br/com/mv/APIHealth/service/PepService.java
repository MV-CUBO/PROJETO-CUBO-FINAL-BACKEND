package br.com.mv.APIHealth.service;


import br.com.mv.APIHealth.domain.enums.EStatePatient;
import br.com.mv.APIHealth.rest.dto.GetPepDTO;
import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PutPepDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PepService {
    public GetPepDTO create(PepDTO pepDTO);
    public GetPepDTO getPepById(UUID id);
    public GetPepDTO updateById(UUID id, PutPepDTO pepDTO);
    public List<GetPepDTO> getAll();
    public void deleteById(UUID id);
    public GetPepDTO findPepByPatientId(UUID patientId);
    public void updateDatePep(LocalDateTime date);
    public List<GetPepDTO> getAllByStatus(EStatePatient status);
    public long getNumInStatus(EStatePatient status);
}
