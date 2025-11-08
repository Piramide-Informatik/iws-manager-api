package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.EmployeeIws;

public interface EmployeeIwsService {

    EmployeeIws create(EmployeeIws employeeIwsEmployeeIws);
    Optional<EmployeeIws> findById(Long id);
    List<EmployeeIws> findAll();
    EmployeeIws update(Long id, EmployeeIws employeeIwsEmployeeIws);
    void delete(Long id);

    // FIND ALL
    List<EmployeeIws> getAllByOrderByLastnameAsc();
    List<EmployeeIws> getAllByOrderByFirstnameAsc();

    List<EmployeeIws> getAllByOrderByIdDesc();
    //PROPERTIES
    List<EmployeeIws> getByActive(Integer active);
    List<EmployeeIws> getByEmployeeLabel(String employeelabel);
    List<EmployeeIws> getByEmployeeNo(Integer employeeno);
    List<EmployeeIws> getByEndDate(LocalDate endDate);
    List<EmployeeIws> getByFirstname(String firstname);
    List<EmployeeIws> getByLastname(String lastname);
    List<EmployeeIws> getByMail(String mail);
    List<EmployeeIws> getByStartDate(LocalDate startDate);

    List<EmployeeIws> getByTeamIwsId(Long teamIwsId);
    List<EmployeeIws> getByUserId(Long userId);

    //HELPERS
    List<EmployeeIws> getByStartDateAfter(LocalDate date);
    List<EmployeeIws> getByStartDateBefore(LocalDate date);
    List<EmployeeIws> getByStartDateBetween(LocalDate start, LocalDate end);
    List<EmployeeIws> getByEndDateAfter(LocalDate date);
    List<EmployeeIws> getByEndDateBefore(LocalDate date);
    List<EmployeeIws> getByEndDateBetween(LocalDate start, LocalDate end);
    
    // ACTIVE - ORDER
    List<EmployeeIws> getByActiveOrderByFirstnameAsc(Integer active);
    List<EmployeeIws> getByActiveOrderByLastnameAsc(Integer active);

    Integer getNextEmployeeNo();

    //CREATE
    EmployeeIws createWithAutoEmployeeNo(EmployeeIws employeeIws);
}
