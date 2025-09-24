package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeIwsRepository extends JpaRepository<EmployeeIws, Long> {
    
    // FIND ALL
    List<EmployeeIws> findAllByOrderByLastnameAsc();
    List<EmployeeIws> findAllByOrderByFirstnameAsc();
    //PROPERTIES
    List<EmployeeIws> findByActive(Integer active);
    List<EmployeeIws> findByEmployeeLabel(String employeelabel);
    List<EmployeeIws> findByEmployeeNo(Integer employeeno);
    List<EmployeeIws> findByEndDate(LocalDate enddate);
    List<EmployeeIws> findByFirstname(String firstname);
    List<EmployeeIws> findByLastname(String lastname);
    List<EmployeeIws> findByMail(String mail);
    List<EmployeeIws> findByStartDate(LocalDate startdate);

    List<EmployeeIws> findByTeamIwsId(Long teamIwsId);
    List<EmployeeIws> findByUserId(Long userId);

    //HELPERS
    List<EmployeeIws> findByStartDateAfter(LocalDate date);
    List<EmployeeIws> findByStartDateBefore(LocalDate date);
    List<EmployeeIws> findByStartDateBetween(LocalDate start, LocalDate end);
    List<EmployeeIws> findByEndDateAfter(LocalDate date);
    List<EmployeeIws> findByEndDateBefore(LocalDate date);
    List<EmployeeIws> findByEndDateBetween(LocalDate start, LocalDate end);
    
    // ACTIVE - ORDER
    List<EmployeeIws> findByActiveOrderByFirstnameAsc(Integer active);
    List<EmployeeIws> findByActiveOrderByLastnameAsc(Integer active);

    @Query("SELECT COALESCE(MAX(e.employeeNo), 0) FROM EmployeeIws e")
    Integer findMaxEmployeeNo();
}