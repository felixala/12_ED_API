package com.felixlaura.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "ELIBILITY_DTLS")
public class EligDtlsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edTradeId;

    private Long caseNum;

    private String holderName;

    private Long holderSsn;

    private String planName;

    private String planStatus;

    private String planStartDate;

    private String planEndDate;

    private Double benefitAmt;

    private String denialReason;

}
