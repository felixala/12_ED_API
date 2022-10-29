package com.felixlaura.repository;

import com.felixlaura.entity.DcIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface DcIncomeRepo extends JpaRepository<DcIncomeEntity, Serializable> {

    DcIncomeEntity findByCaseNum(Long caseNum);
}
