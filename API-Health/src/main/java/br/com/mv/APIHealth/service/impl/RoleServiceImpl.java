package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public RoleDTO create(RoleDTO roleDto) {
        return null;
    }

    @Override
    public RoleDTO getById(UUID id) {
        return null;
    }

    @Override
    public RoleDTO update(UUID id, RoleDTO roleDto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public RoleDTO getByDescription(String description) {
        return null;
    }
}
