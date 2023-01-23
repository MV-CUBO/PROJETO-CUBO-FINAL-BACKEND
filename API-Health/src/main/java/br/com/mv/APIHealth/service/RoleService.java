package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.rest.dto.UserDTO;

import java.util.UUID;

public interface RoleService {

    public RoleDTO create(RoleDTO roleDto);

    public RoleDTO getById(UUID id);

    public RoleDTO update(UUID id, RoleDTO roleDto);

    public void delete(UUID id);

    public RoleDTO getByDescription(String description);

}
