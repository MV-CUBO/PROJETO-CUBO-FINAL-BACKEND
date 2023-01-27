package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "tb_patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Person  {
    private String insuranceCompany;

    private String healthInsurenceCard;

    private String observation;

    @OneToOne
    @JoinColumn(name = "pep_id", referencedColumnName = "id")
    private Pep pep;
}
