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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private UUID id;

    @NotEmpty(message = "{required.name.field}")
    private String name;

    @NotEmpty(message = "vazio teste milton")
    @CPF(message = "nao valido testando milton")
    private String cpf;

    @Past
    private LocalDate dateOfBirth;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAT;

    private Address address;

    @Enumerated(EnumType.STRING)
    private EStatus status;


}
