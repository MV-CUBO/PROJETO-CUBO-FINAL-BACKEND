package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Doctor;
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
