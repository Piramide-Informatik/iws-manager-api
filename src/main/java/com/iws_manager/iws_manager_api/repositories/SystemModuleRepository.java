package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.SystemModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemModuleRepository extends JpaRepository<SystemModule,Long> {
    List<SystemModule> findAllByOrderByNameAsc();
}
