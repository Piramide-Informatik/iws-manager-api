package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {
    List<SystemParameter> findAllByOrderByNameAsc();

     // CREATION - verify if name is duplicated
    boolean existsByName(String name);
    
    // UPDATING - verify if name is duplicated excluding current item
    boolean existsByNameAndIdNot(String name, Long id);
}