package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO extends PersonDTO {

    @NotBlank(message = "{required.crm.field}")
    //@Pattern(regexp = "\\d{4,6}") --limitar a quantidade de caracteres
    private String crm;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
}
