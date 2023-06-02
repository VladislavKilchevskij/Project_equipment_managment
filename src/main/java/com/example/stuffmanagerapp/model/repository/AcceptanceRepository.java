package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Acceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface AcceptanceRepository extends JpaRepository<Acceptance, Integer> {
    List<Acceptance> findAllById(Integer id);
    List<Acceptance> findAllByProviderContainingIgnoreCase(String provider);
    List<Acceptance> findAllByTotalCoast(BigDecimal totalCoast);
    List<Acceptance> findAllByAcceptanceDate(Date acceptanceDate);

}