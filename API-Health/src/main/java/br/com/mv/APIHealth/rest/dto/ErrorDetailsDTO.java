package br.com.mv.APIHealth.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetailsDTO {
    private LocalDateTime timestamp;

    private String message;

    private String details;

}
