package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.domain.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientDTO {
    private UUID id;

    private String insuranceCompany;

    private String healthInsurenceCard;

    private String observation;

    private EStatus status;

    private String name;

    @CPF
    private String cpf;

    @Past
    private LocalDate dateOfBirth;

    private String phone;

    private String email;
    @Enumerated(EnumType.STRING)

    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAT;

    private Address address;
}
