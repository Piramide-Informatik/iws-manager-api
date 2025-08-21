package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeIwsRepository extends JpaRepository<EmployeeIws, Long> {
    
    // FIND ALL
    List<EmployeeIws> findAllByOrderByLastnameAsc();
    List<EmployeeIws> findAllByOrderByFirstnameAsc();
    //PROPERTIES
    List<EmployeeIws> findByActive(Integer active);
    List<EmployeeIws> findByEmployeelabel(String employeelabel);
    List<EmployeeIws> findByEmployeeno(Integer employeeno);
    List<EmployeeIws> findByEnddate(LocalDate enddate);
    List<EmployeeIws> findByFirstname(String firstname);
    List<EmployeeIws> findByLastname(String lastname);
    List<EmployeeIws> findByMail(String mail);
    List<EmployeeIws> findByStartdate(LocalDate startdate);

    List<EmployeeIws> findByTeamIwsId(Long teamIwsId);
    List<EmployeeIws> findByUserId(Long userId);

    //HELPERS
    List<EmployeeIws> findByStartdateAfter(LocalDate date);
    List<EmployeeIws> findByStartdateBefore(LocalDate date);
    List<EmployeeIws> findByStartdateBetween(LocalDate start, LocalDate end);
    List<EmployeeIws> findByEnddateAfter(LocalDate date);
    List<EmployeeIws> findByEnddateBefore(LocalDate date);
    List<EmployeeIws> findByEnddateBetween(LocalDate start, LocalDate end);
    
    // ACTIVE - ORDER
    List<EmployeeIws> findByActiveOrderByFirstnameAsc(Integer active);
    List<EmployeeIws> findByActiveOrderByLastnameAsc(Integer active);

}