package br.com.mv.APIHealth.domain.entity;


import br.com.mv.APIHealth.domain.enums.Specialty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Doctor extends Person {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String crm;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;




}
