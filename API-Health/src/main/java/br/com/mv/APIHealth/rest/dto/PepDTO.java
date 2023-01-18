package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDTO {
    private String pepNumber;

    private UUID patientId;

    private UUID doctorId;

    private String status;

    private String prescription;

    private String boodType;

    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}