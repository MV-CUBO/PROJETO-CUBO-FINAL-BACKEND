package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDTO {

    private UUID id;


    private Integer pepNumber;

    private Patient patient;

    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private EStatePatient status;

    @NotEmpty(message = "{required.prescription.field}")
    private String prescription;

    @NotEmpty(message = "{required.bloodType.field}")
    private String bloodType;

    @NotEmpty(message = "{required.allergies.field}")
    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @JsonIgnore
    private List<PepLog> pepLogs;

    public PepDTO(UUID id, Integer pepNumber, Patient patient, Doctor doctor, EStatePatient status, String prescription, String bloodType, String allergies, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.pepNumber = pepNumber;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.prescription = prescription;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public PepDTO(GetPepDTO pepDTO){
        this.id = pepDTO.getId();
        this.pepNumber = pepDTO.getPepNumber();
        this.patient.setId(pepDTO.getPatientId());
        this.doctor.setId(pepDTO.getDoctorId());
        this.status = pepDTO.getStatus();
        this.prescription = pepDTO.getPrescription();
        this.bloodType = pepDTO.getBloodType();
        this.allergies = pepDTO.getAllergies();
    }
}
