package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String zipCode;

    private String street;

    private String number;

    private String district;

    private String city;

    private String state;

    private String complements;
}
