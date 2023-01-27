package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutPepDTO {
    private UUID id;

    private Integer pepNumber;

    private Patient patient;


    private Doctor doctor;


    private EStatePatient status;


    private String prescription;


    private String bloodType;


    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

}
