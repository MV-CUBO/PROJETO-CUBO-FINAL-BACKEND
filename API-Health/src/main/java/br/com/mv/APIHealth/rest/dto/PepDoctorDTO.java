package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDoctorDTO extends PersonDTO{
    private String crm;

    private Specialty specialty;

}
