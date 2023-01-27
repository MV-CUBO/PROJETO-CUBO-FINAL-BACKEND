package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseDTO extends PersonDTO {

    @NotEmpty(message = "{required.coren.field}")
    private String coren;


}
