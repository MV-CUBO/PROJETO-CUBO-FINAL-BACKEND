package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPepDTO {
    private UUID id;

    @NotEmpty(message = "{required.pepNumber.field}")
    private String pepNumber;


    private UUID patient;


    private UUID doctor;

    @Enumerated(EnumType.STRING)
    private EStatePatient status;


    private String prescription;


    private String bloodType;


    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public GetPepDTO(UUID id, String pepNumber, Patient patient, Doctor doctor, EStatePatient status, String prescription, String bloodType, String allergies, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.pepNumber = pepNumber;
        this.patient = patient.getId();
        this.doctor = doctor.getId();
        this.status = status;
        this.prescription = prescription;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public GetPepDTO(PepDTO pepDTO) {
        this.id = pepDTO.getId();
        this.pepNumber = pepDTO.getPepNumber();
        this.patient = pepDTO.getPatient().getId();
        this.doctor = pepDTO.getDoctor().getId();
        this.status = pepDTO.getStatus();
        this.prescription = pepDTO.getPrescription();
        this.bloodType = pepDTO.getBloodType();
        this.allergies = pepDTO.getAllergies();
        this.createdAt = pepDTO.getCreatedAt();
        this.updateAt =  pepDTO.getUpdateAt();
    }
}
