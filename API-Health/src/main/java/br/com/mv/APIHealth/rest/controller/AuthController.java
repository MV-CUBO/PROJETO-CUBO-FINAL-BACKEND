package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.domain.entity.login.Role;
import br.com.mv.APIHealth.domain.entity.login.User;
import br.com.mv.APIHealth.rest.dto.AuthResponseDTO;
import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.security.JWTGenerator;
import br.com.mv.APIHealth.service.RoleService;
import br.com.mv.APIHealth.service.UserService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(token);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
