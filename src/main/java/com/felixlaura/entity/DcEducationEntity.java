package com.felixlaura.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DC_EDUCATION")
public class DcEducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eduId;

    private Long caseNum;

    private String highestQualification;

    private Integer graduationYear;

    private String universityName;
}
