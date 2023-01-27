package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.GetPepLogDTO;
import br.com.mv.APIHealth.rest.dto.PepLogDTO;

import java.util.List;
import java.util.UUID;

public interface PepLogService {
    public GetPepLogDTO create(PepLogDTO pepLogDTO);
    public GetPepLogDTO getPepById(UUID id);
    public GetPepLogDTO updateById(UUID id, PepLogDTO pepLogDTO);
    public List<GetPepLogDTO> getAll();
    public void deleteById(UUID id);
    public List<GetPepLogDTO> getAllByPepId(UUID pepId);
    public void deleteAllByPepId(UUID pepId);
}
