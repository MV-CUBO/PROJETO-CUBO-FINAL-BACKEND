package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Address;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.domain.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private UUID id;

    private String name;

    private String cpf;

    private Address address;

    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updateAT;
}
