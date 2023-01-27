package br.com.mv.APIHealth.rest.dto;

import br.com.mv.APIHealth.domain.entity.Pep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepLogDTO {
    private Pep pep;

    private String action;

    private LocalDateTime createdAt;
}
