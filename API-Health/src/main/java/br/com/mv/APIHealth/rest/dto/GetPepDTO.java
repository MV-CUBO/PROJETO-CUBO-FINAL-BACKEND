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


    private String pepNumber;


    private UUID patientId;


    private UUID doctorId;


    private EStatePatient status;


    private String prescription;


    private String bloodType;


    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public GetPepDTO(UUID id, String pepNumber, Patient patient, Doctor doctor, EStatePatient status, String prescription, String bloodType, String allergies, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.pepNumber = pepNumber;
        this.patientId = patient.getId();
        this.doctorId = doctor.getId();
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
        this.patientId = pepDTO.getPatient().getId();
        this.doctorId = pepDTO.getDoctor().getId();
        this.status = pepDTO.getStatus();
        this.prescription = pepDTO.getPrescription();
        this.bloodType = pepDTO.getBloodType();
        this.allergies = pepDTO.getAllergies();
        this.createdAt = pepDTO.getCreatedAt();
        this.updateAt =  pepDTO.getUpdateAt();
    }
    public GetPepDTO(PutPepDTO pepDTO) {
        this.id = pepDTO.getId();
        this.pepNumber = pepDTO.getPepNumber();
        this.patientId = pepDTO.getPatient().getId();
        this.doctorId = pepDTO.getDoctor().getId();
        this.status = pepDTO.getStatus();
        this.prescription = pepDTO.getPrescription();
        this.bloodType = pepDTO.getBloodType();
        this.allergies = pepDTO.getAllergies();
        this.createdAt = pepDTO.getCreatedAt();
        this.updateAt =  pepDTO.getUpdateAt();
    }
}
