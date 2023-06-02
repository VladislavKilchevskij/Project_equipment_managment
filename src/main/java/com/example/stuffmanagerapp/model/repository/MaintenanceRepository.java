package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
    @Query("select m from Maintenance m where m.request.id = ?1")
    Maintenance getByRequestId(Integer id);
}