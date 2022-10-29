package com.felixlaura.repository;

import com.felixlaura.entity.DcEducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface DcEducationRepo extends JpaRepository<DcEducationEntity, Serializable> {

    DcEducationEntity findByCaseNum(Long caseNum);
}
