package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Person  {
    private String insuranceCompany;
    private String healthInsurenCard;
    private String observation;
    private String status;
}
