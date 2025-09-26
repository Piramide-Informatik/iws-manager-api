package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import com.iws_manager.iws_manager_api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String USERNOTFOUND = "User not found";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    @Override
    public User update(Long id, User userDetails) {
        if (id == null || userDetails == null){
         throw new IllegalArgumentException("Id and user details cannot be null");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userDetails.getFirstName());
                    existingUser.setLastName(userDetails.getLastName());
                    existingUser.setActive(userDetails.isActive());
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setPassword(userDetails.getPassword());
                    existingUser.setUsername(userDetails.getUsername());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        int roleCount = roleRepository.findByUserId(id).size();

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


}
