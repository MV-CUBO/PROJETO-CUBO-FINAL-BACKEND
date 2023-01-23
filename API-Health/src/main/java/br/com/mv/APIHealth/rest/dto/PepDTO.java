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


    private String pepNumber;

<<<<<<< HEAD
    private UUID patientId;

    private UUID doctorId;

    private String status;

    private String prescription;

    private String bloodType;

    private String allergies;

=======
    @NotEmpty
    private UUID patientId;

    @NotEmpty
    private UUID doctorId;

    @NotEmpty
    private String status;

    @NotEmpty
    private String prescription;

    @NotEmpty
    private String bloodType;

    @NotEmpty
    private String allergies;

    @NotEmpty
>>>>>>> ae33022393805115badc0ec3d805896c8ee80608
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
