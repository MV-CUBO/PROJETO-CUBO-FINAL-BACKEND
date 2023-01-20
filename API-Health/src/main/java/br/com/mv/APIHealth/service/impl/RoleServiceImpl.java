package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.login.Role;
import br.com.mv.APIHealth.domain.repository.RoleRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO create(RoleDTO roleDto) {
        Role role = new Role();
        roleDto.setId(null);
        BeanUtils.copyProperties(roleDto, role);
        Role newRole = roleRepository.save(role);

        BeanUtils.copyProperties(newRole, roleDto);
        return roleDto;
    }

    @Override
    public RoleDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role not found.")
        );

        RoleDTO roleDto = new RoleDTO();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }

    @Override
    public RoleDTO update(UUID id, RoleDTO roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role not found.")
        );

        roleDto.setId(role.getId());
        BeanUtils.copyProperties(roleDto, role);
        Role updatedRole = roleRepository.save(role);
        BeanUtils.copyProperties(updatedRole, roleDto);
        return roleDto;
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDTO getByDescription(String description) {
        return = roleRepository.getByDescription(description).orElseThrow(
                () -> new ResourceNotFoundException("Role not found.")
        );
    }
}
