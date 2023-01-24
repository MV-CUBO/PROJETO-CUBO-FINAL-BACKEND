package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Pep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO extends PersonDTO {

    @NotEmpty(message = "{required.insuranceCompany.field}")
    private String insuranceCompany;

    @NotEmpty(message = "{required.healthInsurenceCard.field}")
    private String healthInsurenceCard;

    @NotEmpty(message = "{required.observation.field}")
    private String observation;

    @Valid
    private Pep pep;
}
