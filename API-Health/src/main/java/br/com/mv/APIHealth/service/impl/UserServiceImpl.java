package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.login.User;
import br.com.mv.APIHealth.domain.repository.UserRepository;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserServiceImpl(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDTO create(UserDTO userDto) {
        User user = new User();
        userDto.setId(null);
        BeanUtils.copyProperties(userDto, user);
        User newUser = userRepository.save(user); // TODO password must be encoded.

        BeanUtils.copyProperties(newUser, userDto);
        return userDto;
    }

    @Override
    public UserDTO getUserById(UUID id) {
        Optional<User> userOptional =  userRepository.findById(id);
        if (!userOptional.isPresent()) {
            String userNotFoundMessage = messageSource.getMessage("noExist.idUser.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(userNotFoundMessage);
        }

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userOptional.get(), dto);
        return dto;
    }

    @Override
    public UserDTO update(UUID id, UserDTO userDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("{noExist.idUser.field}")
        );

        userDto.setId(user.getId());
        this.updateNonNullableFields(userDto, user);
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
                () -> new ResourceNotFoundException("{noExist.idUser.field}")
        );
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    private void updateNonNullableFields(UserDTO originUserDto, User targetUser) {
        if (originUserDto.getUsername() != null) {
            targetUser.setUsername(originUserDto.getUsername());
        }
        if (originUserDto.getPassword() != null) {
            targetUser.setPassword(originUserDto.getPassword());
        }
    }
}
