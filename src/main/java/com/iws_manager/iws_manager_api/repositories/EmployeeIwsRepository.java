package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeIwsRepository extends JpaRepository<EmployeeIws, Long> {
    
    // FIND ALL
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findAllByOrderByLastnameAsc();
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findAllByOrderByFirstnameAsc();

    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findAllByOrderByIdDesc();
    //PROPERTIES
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByActive(Integer active);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEmployeeLabel(String employeelabel);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEmployeeNo(Integer employeeno);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEndDate(LocalDate enddate);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByFirstname(String firstname);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByLastname(String lastname);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByMail(String mail);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByStartDate(LocalDate startdate);

    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByTeamIwsId(Long teamIwsId);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByUserId(Long userId);

    //HELPERS
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByStartDateAfter(LocalDate date);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByStartDateBefore(LocalDate date);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByStartDateBetween(LocalDate start, LocalDate end);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEndDateAfter(LocalDate date);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEndDateBefore(LocalDate date);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByEndDateBetween(LocalDate start, LocalDate end);
    
    // ACTIVE - ORDER
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByActiveOrderByFirstnameAsc(Integer active);
    @EntityGraph(attributePaths = { "teamIws", "user"})
    List<EmployeeIws> findByActiveOrderByLastnameAsc(Integer active);

    @EntityGraph(attributePaths = { "teamIws", "user"})
    @Query("SELECT COALESCE(MAX(e.employeeNo), 0) FROM EmployeeIws e")
    Integer findMaxEmployeeNo();
}