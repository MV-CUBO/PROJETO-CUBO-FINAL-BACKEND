package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_nurse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse extends Person {

    private String coren;
}
