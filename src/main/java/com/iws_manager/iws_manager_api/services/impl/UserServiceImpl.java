package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import com.iws_manager.iws_manager_api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        return userRepository.findAll();
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
                    existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    existingUser.setUsername(userDetails.getUsername());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        userRepository.deleteById(id);
    }
}
