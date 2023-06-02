package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.RepairHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface RepairHistoryRepository extends JpaRepository<RepairHistory, Integer> {

    @Transactional
    @Modifying
    @Query("update RepairHistory r " +
            "set r.renderedService = ?1, " +
            "r.serviceCoast = ?2 " +
            "where r.id = ?3")
    void updateMaintenanceInfo(String renderedService, BigDecimal coast, Integer id);
}