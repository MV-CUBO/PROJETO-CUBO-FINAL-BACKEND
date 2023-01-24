package br.com.mv.APIHealth.domain.entity;

import br.com.mv.APIHealth.domain.enums.EStatePatient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_pep")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pep{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String pepNumber;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    //private UUID doctorId;

    @Enumerated(EnumType.STRING)
    private EStatePatient status;

    private String prescription;

    private String bloodType;

    private String allergies;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @OneToMany(mappedBy="pep")
    private List<PepLog> pepLogs;
}