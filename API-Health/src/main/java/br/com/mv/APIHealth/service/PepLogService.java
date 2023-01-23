package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;

import java.util.List;
import java.util.UUID;

public interface PepLogService {
    public PepLogDTO create(PepLogDTO pepLogDTO);
    public PepLogDTO getPepById(UUID id);
    public PepLogDTO updateById(UUID id, PepLogDTO pepLogDTO);
    public List<PepLogDTO> getAll();
    public void deleteById(UUID id);
}
