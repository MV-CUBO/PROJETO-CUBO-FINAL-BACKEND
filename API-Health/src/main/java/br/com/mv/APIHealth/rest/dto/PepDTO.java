package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDTO {

    @NotEmpty
    private String pepNumber;
    @NotEmpty
    private UUID patientId;
    @NotEmpty
    private UUID doctorId;
    @NotEmpty
    private String status;
    @NotEmpty
    private String prescription;
    @NotEmpty
    private String boodType;
    @NotEmpty
    private String allergies;
    @NotEmpty
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
