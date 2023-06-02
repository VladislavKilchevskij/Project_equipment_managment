package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Writeoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteoffRepository extends JpaRepository<Writeoff, Integer> {
}