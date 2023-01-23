package br.com.mv.APIHealth.domain.entity;


import br.com.mv.APIHealth.domain.enums.Specialty;
import br.com.mv.APIHealth.rest.dto.DoctorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Doctor extends Person {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @OneToMany(mappedBy="doctor")
    private List<Pep> peps;


}
