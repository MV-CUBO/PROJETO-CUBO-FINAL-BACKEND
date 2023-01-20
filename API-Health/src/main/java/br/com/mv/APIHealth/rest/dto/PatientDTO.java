package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO extends PersonDTO{
    private String insuranceCompany;

    private String healthInsurenceCard;

    private String observation;

    private EStatus status;

    private Pep pep;
}
