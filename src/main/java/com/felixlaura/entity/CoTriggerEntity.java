package com.felixlaura.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CoTriggerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trgId;

    private Long caseNum;

    private byte[] coPdf;

    private String trgStatus;
}
