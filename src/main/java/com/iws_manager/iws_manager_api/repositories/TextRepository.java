package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {
    List<Text> findAllByOrderByLabelAsc();

    // CREATION - verify if label is duplicated
    boolean existsByLabel(String label);
    
    // UPDATING - verify if label is duplicated
    boolean existsByLabelAndIdNot(String label, Long id);
}