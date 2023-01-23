package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDTO {


    private UUID id;

    @NotEmpty
    private String pepNumber;

    @NotEmpty
<<<<<<< HEAD
    private UUID patientId;

    @JsonIgnore
    @NotEmpty
    private Doctor doctor;

    @NotEmpty
=======
>>>>>>> 4410edb66066c2facfc915607953fdc555c66204
    private String status;

    @NotEmpty
    private String prescription;

    @NotEmpty
    private String bloodType;

    @NotEmpty
    private String allergies;


    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
