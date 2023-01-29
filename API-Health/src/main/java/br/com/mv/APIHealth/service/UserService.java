package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.UpdateUserDTO;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserDTO create(UserDTO userDto);
    public  UserDTO getUserById(UUID id);
    public UpdateUserDTO update(UUID id, UpdateUserDTO userDto);
    public void delete(UUID id);
    public Page<UserDTO> getAllUsers(Pageable pageable);

    public UserDTO getUserByUsername(String username);
    public boolean existsByUsername(String username);

    public UserDTO findByEmail(String email);
}
