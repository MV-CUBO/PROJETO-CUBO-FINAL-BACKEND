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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNurseDTO {
    private UUID id;

    private String name;

    @CPF(message = "{required.cpf.invalid.field}")
    private String cpf;

    @Past(message = "{required.date.field}")
    @NotNull(message = "{required.dateEmpyt.field}")
    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String coren;

    private Address address;

    private EStatus status;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAT;
}
