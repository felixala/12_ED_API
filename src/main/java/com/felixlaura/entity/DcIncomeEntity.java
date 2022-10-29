package com.felixlaura.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DC_INCOME")
public class DcIncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;

    private Long caseNum;

    private Double empIncome;

    private Double propertyIncome;
}
