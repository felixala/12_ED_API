package com.felixlaura.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DC_CASES")
public class DcCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseNum;

    private Integer appId;

    private Integer planId;

}
