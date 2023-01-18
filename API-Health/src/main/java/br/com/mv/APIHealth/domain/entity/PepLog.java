package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_pepLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PepLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pepId;
    private String action;
    private LocalDateTime createdAt;



}
