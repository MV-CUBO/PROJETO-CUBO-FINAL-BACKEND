package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPepLogDTO {
    private UUID pepId;

    private String action;

    private LocalDateTime createdAt;

    public GetPepLogDTO(PepLogDTO pepLogDTO) {
        this.pepId = pepLogDTO.getPep().getId();
        this.action = pepLogDTO.getAction();
        this.createdAt = pepLogDTO.getCreatedAt();
    }
}
