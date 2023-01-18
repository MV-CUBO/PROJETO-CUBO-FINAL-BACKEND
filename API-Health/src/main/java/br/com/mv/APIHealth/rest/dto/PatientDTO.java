package br.com.mv.APIHealth.rest.dto;

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

    private String status;
}
