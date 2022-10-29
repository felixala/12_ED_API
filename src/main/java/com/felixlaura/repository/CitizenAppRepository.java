package com.felixlaura.repository;

import com.felixlaura.entity.CitizenAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenAppRepository extends JpaRepository<CitizenAppEntity, Integer> {

}
