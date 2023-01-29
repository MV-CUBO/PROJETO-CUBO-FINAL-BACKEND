package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.domain.enums.MaritalStatus;
import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDoctorDTO {
    private String crm;

    private Specialty specialty;


    private UUID id;

    private String name;

    @CPF(message = "{required.cpf.invalid.field}")
    private String cpf;

    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Address address;

    private EStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updateAT;
}
