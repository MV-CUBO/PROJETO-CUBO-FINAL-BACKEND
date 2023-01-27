package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private UUID id;

    @NotEmpty(message = "{required.description.field}")
    private String description;
}
