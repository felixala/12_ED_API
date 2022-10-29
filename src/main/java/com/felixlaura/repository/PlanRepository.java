package com.felixlaura.repository;

import com.felixlaura.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable> {

    PlanEntity findByPlanName(String planName);

}
