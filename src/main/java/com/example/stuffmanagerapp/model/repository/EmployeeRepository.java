package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllById(Integer id);
    List<Employee> findAllByPositionContainingIgnoreCase(String position);
    @Query("select e from Employee e where upper(e.department.departmentName) like upper(concat('%', ?1, '%'))")
    List<Employee> findAllByDepartmentDepartmentName(String department_departmentName);

    @Transactional
    @Modifying
    @Query("update Employee e " +
            "set e.firstName = :#{#editEmployee.firstName}, " +
            "e.surname = :#{#editEmployee.surname}, " +
            "e.patronymic = :#{#editEmployee.patronymic}, " +
            "e.position = :#{#editEmployee.position}, " +
            "e.isChief = :#{#editEmployee.isChief}, " +
            "e.department = :#{#editEmployee.department} " +
            "where e.id = :#{#editEmployee.id}")
    void updateEmployeeInfo(@Param("editEmployee") Employee editEmployee);
}