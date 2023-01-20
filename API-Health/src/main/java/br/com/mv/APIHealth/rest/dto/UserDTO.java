package br.com.mv.APIHealth.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @NotEmpty(message = "{required.username.field}")
    private String username;

    @JsonIgnore
    @NotEmpty(message = "{required.username.field}")
    private String password;
}
