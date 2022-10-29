package com.felixlaura.repository;

import com.felixlaura.entity.DcCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface DcCaseRepo extends JpaRepository<DcCaseEntity, Serializable> {

    DcCaseEntity findByAppId(Integer appId);
}
