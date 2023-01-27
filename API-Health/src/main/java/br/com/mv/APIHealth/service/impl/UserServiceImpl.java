package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.login.Role;
import br.com.mv.APIHealth.domain.entity.login.User;
import br.com.mv.APIHealth.domain.repository.UserRepository;
import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.exception.ResourceNotFoundException;
import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.service.RoleService;
import br.com.mv.APIHealth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, MessageSource messageSource,
                           PasswordEncoder passwordEncoder, RoleService roleService,
                           EntityManager entityManager) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDto) {
        this.validateUserExists(userDto.getUsername());

        userDto.setId(null);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(userDto, user);


        List<Role> roles = new ArrayList<>();

        for (String roleDescription : userDto.getRole()) {
            RoleDTO roleDto = roleService.getByDescription(roleDescription);
            Role role = new Role();
            BeanUtils.copyProperties(roleDto, role);
            role = entityManager.merge(role);
            roles.add(role);
        }

        user.setRole(roles);

        User newUser = userRepository.save(user);
        BeanUtils.copyProperties(newUser, userDto);
        return userDto;
    }

    @Override
    public UserDTO getUserById(UUID id) {
        Optional<User> userOptional =  userRepository.findById(id);
        if (userOptional.isEmpty()) {
            String userNotFoundMessage = messageSource.getMessage("noExist.idUser.field",
                    null, Locale.getDefault());
            throw new ResourceNotFoundException(userNotFoundMessage);
        }

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userOptional.get(), dto);
        return dto;
    }

    @Override
    @Transactional
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

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = this.userRepository.findByUsername(email).orElseThrow(() -> new BadRequestException("error"));
        UserDTO userdto = new UserDTO();

        BeanUtils.copyProperties(user, userdto);

        List<String> arrayRoles = user.getRole().stream().map(role -> role.getDescription())
                .collect(Collectors.toList());

        userdto.setRole(arrayRoles);
        return userdto;
    }

    private void updateNonNullableFields(UserDTO originUserDto, User targetUser) {
        if (originUserDto.getUsername() != null) {
            targetUser.setUsername(originUserDto.getUsername());
        }
        if (originUserDto.getPassword() != null) {
            targetUser.setPassword(originUserDto.getPassword());
        }
    }

    private void validateUserExists(String username) {
        boolean userIsPresent = this.userRepository.findByUsername(
                username).isPresent();

        if (userIsPresent) throw new BadRequestException("{exist.user.field}");
    }
}
