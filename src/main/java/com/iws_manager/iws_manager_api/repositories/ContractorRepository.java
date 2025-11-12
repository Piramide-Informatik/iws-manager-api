package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.models.Contractor;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor,Long> {
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    Optional<Contractor> findById(Long id);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    List<Contractor> findAllByOrderByNameAsc();

    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    List<Contractor> findByCustomerId(Long customerId);

    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    List<Contractor> findByCountryId(Long countryId);

    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    List<Contractor> findByCustomerIdOrderByLabelAsc(Long customerId);

    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state","country"})
    List<Contractor> findByCustomerIdOrderByNameAsc(Long customerId);

    // VALIDATIONS
    // Label must be unique
    // CREATION - verify if label exists for specific customer (case-insensitive)
    @Query("SELECT COUNT(c) > 0 FROM Contractor c WHERE UPPER(c.label) = UPPER(:label) AND c.customer.id = :customerId")
    boolean existsByLabelIgnoreCaseAndCustomerId(@Param("label") String label, @Param("customerId") Long customerId);
    
    // UPDATING - verify if label exists for specific customer with exception of the same item (case-insensitive)
    @Query("SELECT COUNT(c) > 0 FROM Contractor c WHERE UPPER(c.label) = UPPER(:label) AND c.customer.id = :customerId AND c.id != :excludeId")
    boolean existsByLabelIgnoreCaseAndCustomerIdAndIdNot(@Param("label") String label, @Param("customerId") Long customerId, @Param("excludeId") Long excludeId);
}
