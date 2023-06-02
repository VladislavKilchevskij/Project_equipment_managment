package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Maintenance;
import com.example.stuffmanagerapp.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByMaintenanceId(Integer id);
    List<Request> findAllByMaintenanceServiceDate(Date serviceDate);
    List<Request> findAllByMaintenanceTotalServiceCost(BigDecimal totalServiceCost);

    List<Request> findAllByMaintenanceIs(Maintenance maintenance);
    List<Request> findAllByMaintenanceIsNotNull();
}