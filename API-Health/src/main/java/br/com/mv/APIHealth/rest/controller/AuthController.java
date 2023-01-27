package br.com.mv.APIHealth.rest.controller;


import br.com.mv.APIHealth.exception.BadRequestException;
import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.security.DTO.JwtRequestDTO;
import br.com.mv.APIHealth.security.DTO.JwtResponseDTO;
import br.com.mv.APIHealth.security.libs.JwtTokenUtil;
import br.com.mv.APIHealth.security.libs.JwtUserDetailsService;
import br.com.mv.APIHealth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/login")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private void authenticate(String email, String password) throws BadRequestException {
        UserDTO user = this.userService.findByEmail(email);

        if (user == null) throw new BadRequestException("Email not found");
        if (!passwordEncoder.matches(password, user.getPassword())) throw new BadRequestException("Invalid password");
    }

    @PostMapping
    public ResponseEntity<JwtResponseDTO> createAuthenticationToken(@RequestBody JwtRequestDTO response) {
        this.authenticate(response.getEmail(), response.getPassword());

        UserDTO user = this.userService.findByEmail(response.getEmail());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(response.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        JwtResponseDTO jwtResponse = new JwtResponseDTO(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
