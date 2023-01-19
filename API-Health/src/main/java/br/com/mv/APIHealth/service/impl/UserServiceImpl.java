package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.login.User;
import br.com.mv.APIHealth.domain.repository.UserRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO create(UserDTO userDto) {
        User user = new User();
        userDto.setId(null);
        BeanUtils.copyProperties(userDto, user);
        User newUser = userRepository.save(user);

        BeanUtils.copyProperties(newUser, userDto);
        return userDto;
    }

    @Override
    public UserDTO getUserById(UUID id) {
        User user =  userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public UserDTO update(UUID id, UserDTO userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        userDto.setId(user.getId());
        BeanUtils.copyProperties(userDto, user);
        User updatedUser = userRepository.save(user);

        BeanUtils.copyProperties(updatedUser, userDto);
        return userDto;
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

}
