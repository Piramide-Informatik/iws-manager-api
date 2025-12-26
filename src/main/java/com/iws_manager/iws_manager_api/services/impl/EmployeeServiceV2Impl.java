package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.dtos.employee.*;
import com.iws_manager.iws_manager_api.mappers.EmployeeMapper;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeServiceV2;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link EmployeeServiceV2} interface for managing Employee entities with DTOs.
 * Provides CRUD operations and business logic for Employee management using DTOs.
 */
@Service
@Transactional
public class EmployeeServiceV2Impl implements EmployeeServiceV2 {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OrderRepository orderRepository;
    
    @Autowired
    public EmployeeServiceV2Impl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public EmployeeDTO create(EmployeeInputDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Employee DTO cannot be null");
        }
        
        Employee entity = employeeMapper.toEntity(dto);
        Employee savedEntity = employeeRepository.save(entity);
        return employeeMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDetailDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return employeeRepository.findById(id)
                .map(employeeMapper::toDetailDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        List<Employee> entities = employeeRepository.findAllByOrderByEmployeenoAsc();
        return employeeMapper.toDTOList(entities);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeInputDTO dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("ID and employee DTO cannot be null");
        }
        
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    employeeMapper.updateEntityFromDTO(dto, existingEmployee);
                    Employee updatedEntity = employeeRepository.save(existingEmployee);
                    return employeeMapper.toDTO(updatedEntity);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        if (!employeeRepository.existsById(id)) {  
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByLastname(String lastname) {
        List<Employee> entities = employeeRepository.findByLastname(lastname);
        return employeeMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDetailDTO> findByEmail(String email) {
        Employee entity = employeeRepository.findByEmail(email);
        return Optional.ofNullable(entity)
                .map(employeeMapper::toDetailDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByTitleId(Long titleId) {
        List<Employee> entities = employeeRepository.findByTitleId(titleId);
        return employeeMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findBySalutationId(Long salutationId) {
        List<Employee> entities = employeeRepository.findBySalutationId(salutationId);
        return employeeMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByQualificationFZId(Long qualificationFZId) {
        List<Employee> entities = employeeRepository.findByQualificationFZId(qualificationFZId);
        return employeeMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCustomerId(Long customerId) {
        List<Employee> entities = employeeRepository.findByCustomerIdOrderByEmployeenoAsc(customerId);
        return employeeMapper.toDTOList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByEmployeeCategoryId(Long employeeCategoryId) {
        List<Employee> entities = employeeRepository.findByEmployeeCategoryId(employeeCategoryId);
        return employeeMapper.toDTOList(entities);
    }

    // get employees by project id through a specific customer id
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByProjectId(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }

        List<Employee> employees =
                orderRepository.findEmployeesByProjectIdOrderByFirstnameAsc(projectId);

        return employeeMapper.toDTOList(employees);
    }

}