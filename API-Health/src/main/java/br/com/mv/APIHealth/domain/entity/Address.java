package br.com.mv.APIHealth.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {


    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String zipCode;

    private String street;

    private String number;

    private String district;

    private String city;

    private String state;

    private String complements;
}
