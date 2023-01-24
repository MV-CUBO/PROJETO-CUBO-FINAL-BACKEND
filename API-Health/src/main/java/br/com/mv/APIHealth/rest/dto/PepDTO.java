package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepDTO {

    private UUID id;

    @NotEmpty(message = "{required.pepNumber.field}")
    private String pepNumber;

    @Valid
    private UUID patientId;

    @JsonIgnore
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

}
