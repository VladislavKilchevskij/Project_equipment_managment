package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAllById(Integer id);
    List<Department> findAllByDepartmentNameContainingIgnoreCase(String departmentName);
    List<Department> findAllByPhoneNumberContaining(String phoneNumber);
    List<Department> findAllByFloor(Integer floor);
    @Transactional
    @Modifying
    @Query("update Department d " +
            "set d.departmentName = :#{#editDepartment.departmentName}, " +
            "d.floor = :#{#editDepartment.floor}, " +
            "d.phoneNumber = :#{#editDepartment.phoneNumber} " +
            "where d.id = :#{#editDepartment.id}")
    void updateDepartmentInfo(@Param("editDepartment") Department editDepartment);
}