package com.felixlaura.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "CITIZEN_APPS")
public class CitizenAppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appId;

    private String fullName;

    private String email;

    private Long phno;

    private String gender;

    private Long ssn;

    private LocalDate dob;

    private String stateName;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;

    private String createdBy;

    private String updatedBy;
}
