package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_pep")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pep{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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