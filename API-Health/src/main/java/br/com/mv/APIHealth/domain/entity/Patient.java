package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Person  {
    private String insuranceCompany;

    private String healthInsurenceCard;

    private String observation;

    private String status;

    @OneToOne
    @JoinColumn(name = "pep_id", referencedColumnName = "id")
    private Pep pep;
}
