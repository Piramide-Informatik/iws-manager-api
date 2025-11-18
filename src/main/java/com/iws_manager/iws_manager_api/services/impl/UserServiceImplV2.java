package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.user.CreateUserDTO;
import com.iws_manager.iws_manager_api.dtos.user.UpdateUserDTO;
import com.iws_manager.iws_manager_api.mappers.UserMapper;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import com.iws_manager.iws_manager_api.services.interfaces.UserServiceV2;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImplV2 implements UserServiceV2 {
    private static final String USERNOTFOUND = "User not found";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImplV2(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User create(CreateUserDTO dto) {
        User user = UserMapper.toEntity(dto);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllFetchRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findByIdFetchRoles(id);
    }

    @Override
    @Transactional
    public User update(Long id, UpdateUserDTO dto) {
        return userRepository.findById(id)
                .map(existing -> {
                    UserMapper.updateEntity(existing, dto);
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(Long id) {
        int roleCount = roleRepository.findByUserId(id).size();

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        if (roleCount > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is assigned to " + roleCount + " role(s) and cannot be deleted"
            );
        }
        userRepository.deleteById(id);
    }

    @Override
    public User assignRole(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USERNOTFOUND));
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public List<Role> getRolesByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return roleRepository.findByUserId(userId);
    }

    @Override
    public List<String> getRoleNamesByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        // Asumimos que roleRepository.findByUserId(userId) devuelve List<Role>
        List<Role> roles = roleRepository.findByUserId(userId);
        return roles.stream()
                .map(Role::getName)
                .toList();
    }
}
