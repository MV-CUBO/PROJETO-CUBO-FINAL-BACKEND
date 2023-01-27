package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    //@NotEmpty(message = "{required.username.field}")
//    @Range(min = 2, message = "usename field must have more than two characters.")
    private String username;

    //@NotEmpty(message = "{required.username.field}")
    private String password;

    private List<String> role;
}
