package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO extends PersonDTO {

    @NotBlank(message = "{required.crm.field}")
    //@Pattern(regexp = "\\d{4,6}") --limitar a quantidade de caracteres
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;


}
