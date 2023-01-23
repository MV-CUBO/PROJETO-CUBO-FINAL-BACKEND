package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.utils.Response;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public UserDTO create(UserDTO userDto);
    public UserDTO getUserById(UUID id);
    public UserDTO update(UUID id, UserDTO userDto);
    public void delete(UUID id);

    public UserDTO getUserByUsername(String username);
}
