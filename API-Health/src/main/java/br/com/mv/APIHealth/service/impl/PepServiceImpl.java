package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.rest.dto.PepDTO;
import br.com.mv.APIHealth.service.PepService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PepServiceImpl implements PepService {
    @Override
    public PepDTO create(PepDTO pepDTO) {
        return null;
    }

    @Override
    public PepDTO getPepById(UUID id) {
        return null;
    }

    @Override
    public PepDTO updateById(UUID id, PepDTO pepDTO) {
        return null;
    }

    @Override
    public List<PepDTO> getAll() {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void updatePep(LocalDateTime date) {

    }
}
