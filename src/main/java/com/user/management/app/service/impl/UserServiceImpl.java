package com.user.management.app.service.impl;

import com.user.management.app.model.entity.User;
import com.user.management.app.repository.UserRepository;
import com.user.management.app.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation class for CRUD operation for user entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all registered user in system
     *
     * @return
     */
    @Override
    public List<User> getAll(String authorization) {
        return userRepository.findAll();
    }

    /**
     * Retrieve user information
     *
     * @return
     */
    @Override
    public User get(String authorization) {
        return userRepository.findFirstByUserName(authorization);
    }

    /**
     * Update user information
     *
     * @param userInformation
     */
    @Override
    public User update(String authorization, User userInformation) {
        User userToUpdate = userRepository.findFirstByUserName(userInformation.getUserName());
        userToUpdate.setEmail(userInformation.getEmail());
        userToUpdate.setName(userInformation.getName());
        userToUpdate.setPhoneNumber(userInformation.getPhoneNumber());
        return userRepository.save(userToUpdate);
    }
}