package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.SystemFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface SystemFunctionRepository extends JpaRepository<SystemFunction,Long> {

   @EntityGraph(attributePaths = {"module"})
   List<SystemFunction> findByModuleId(Long moduleId);
}
