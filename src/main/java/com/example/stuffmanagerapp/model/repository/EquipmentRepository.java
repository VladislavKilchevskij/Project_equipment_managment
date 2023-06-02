package com.example.stuffmanagerapp.model.repository;

import com.example.stuffmanagerapp.model.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {


    Page<Equipment> findAllByStatusIsNot(String status, Pageable pageable);
    List<Equipment> findAllByStatusIs(String status);


    // Методы поиска по колонкам архива
    List<Equipment> findAllByStatusIsAndId(String status, Integer id);
    List<Equipment> findAllByStatusIsAndEquipmentNameContainingIgnoreCase(String status, String equipmentName);
    @Query("select e from Equipment e where e.status = ?1 and e.writeoff.employee.surname = ?2 and e.writeoff.employee.firstName = ?3 and e.writeoff.employee.patronymic = ?4")
    List<Equipment> findAllByStatusIsAndWriteoffEmployee(String status, String surname, String firstname, String patronymic);
    List<Equipment> findAllByStatusIsAndCategory(String status, String category);
    @Query("select e from Equipment e where e.status = ?1 and e.writeoff.writeoffDate = ?2")
    List<Equipment> findAllByStatusIsAndWriteoffDate(String status, Date date);
    List<Equipment> findAllByStatusIsAndWriteoffReasonContaining(String status, String writeoffReason);


    // Методы поиска по колонкам актуального оборудования
    List<Equipment> findAllByStatusIsNotAndId(String status, Integer id);
    List<Equipment> findAllByStatusIsNotAndEquipmentNameContainingIgnoreCase(String status, String equipmentName);
    List<Equipment> findAllByStatusIsNotAndCoast(String status, BigDecimal coast);
    @Query("select e from Equipment e where e.status <> ?1 and e.employee.surname = ?2 and e.employee.firstName = ?3 and e.employee.patronymic = ?4")
    List<Equipment> findAllByStatusIsNotAndEmployee(String status, String surname, String firstname, String patronymic);
    List<Equipment> findAllByStatusIsNotAndEmployeeIsNull(String status);
    List<Equipment> findAllByStatusIsNotAndCategory(String status, String category);
    List<Equipment> findAllByStatusIsNotAndNextInspectionDate(String status, Date nextInspectionDate);
    List<Equipment> findAllByStatusIsNotAndUsageEndDate(String status, Date usageEndDate);


    // Методы определения оборудования с истекающим сроком
    List<Equipment> findAllByNextInspectionDateBetween(Date start, Date end);

    @Query(value = "select * from project.equipment where equipment.nextInspectionDate REGEXP ?1 or equipment.nextInspectionDate between ?2 and ?3", nativeQuery = true)
    List<Equipment> findAllExpired(String regex, Date nextInspectionDate2, Date nextInspectionDate3);

    // ---------------------------------------------------

    List<Equipment> findAllByEmployeeId(Integer employeeId);

    @Transactional
    @Modifying
    @Query("update Equipment e " +
            "set e.nextInspectionDate = :nextInspectionDate, " +
            "e.employee.id = :employeeId " +
            "where e.id = :id")
    void bindEquipmentUser(@Param("id") Integer id,
                           @Param("nextInspectionDate") Date date,
                           @Param("employeeId") Integer employeeId);

    @Transactional
    @Modifying
    @Query("update Equipment e " +
            "set e.nextInspectionDate = null, " +
            "e.employee = null " +
            "where e.id = :id")
    void unbindEquipmentUser(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update Equipment e " +
            "set e.status = :status " +
            "where e.id = :id")
    void changeStatus(@Param("id") Integer eqiuipId,
                      @Param("status") String status);

    boolean existsByIdAndEmployeeIsNotNull(Integer id);
    boolean existsByIdAndStatusIsNot(Integer id, String status);
}