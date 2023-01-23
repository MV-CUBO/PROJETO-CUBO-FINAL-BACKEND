package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseDTO extends PersonDTO {

    @NotEmpty(message = "{required.coren.field}")
    private String coren;


}
