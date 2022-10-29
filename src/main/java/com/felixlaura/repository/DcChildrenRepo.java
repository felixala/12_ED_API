package com.felixlaura.repository;

import com.felixlaura.entity.DcChildrenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface DcChildrenRepo extends JpaRepository<DcChildrenEntity, Serializable> {

    List<DcChildrenEntity> findByCaseNum(Long caseNum);
}
