package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.PepLog;
import br.com.mv.APIHealth.domain.enums.EStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    private Patient patient;

    @Valid
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private EStatus status;

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
