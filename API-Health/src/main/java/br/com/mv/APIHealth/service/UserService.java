package br.com.mv.APIHealth.service;

import br.com.mv.APIHealth.domain.entity.login.User;
import br.com.mv.APIHealth.rest.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    public UserDTO create(UserDTO userDto);
    public  UserDTO getUserById(UUID id);
    public UserDTO update(UUID id, UserDTO userDto);
    public void delete(UUID id);

    public UserDTO getUserByUsername(String username);
    public boolean existsByUsername(String username);

    public UserDTO findByEmail(String email);
}
