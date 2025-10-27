package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.models.Contractor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
}
