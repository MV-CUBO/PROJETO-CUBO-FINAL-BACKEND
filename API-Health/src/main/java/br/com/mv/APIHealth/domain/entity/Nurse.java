package br.com.mv.APIHealth.domain.entity;

import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import br.com.mv.APIHealth.domain.enums.MaritalStatus;
import br.com.mv.APIHealth.rest.dto.NurseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_nurse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse extends Person {

    private String coren;

    @Enumerated(EnumType.STRING)
    private EStatus status; // validar com grupo se todas as entitys tem q ter status setado

    public Nurse(NurseDTO nurseDTO) {

    }

}
