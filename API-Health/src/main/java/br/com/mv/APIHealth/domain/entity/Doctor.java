package br.com.mv.APIHealth.domain.entity;


import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person {

    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @OneToMany(mappedBy="doctor")
    private List<Pep> peps;


}
