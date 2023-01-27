package br.com.mv.APIHealth.security.DTO;

import lombok.Getter;

@Getter
public class JwtResponseDTO {
    private final String token;

    public JwtResponseDTO(String jwttoken) {
        this.token = jwttoken;
    }
}