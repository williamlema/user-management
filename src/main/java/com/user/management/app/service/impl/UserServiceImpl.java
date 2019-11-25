package com.user.management.app.service.impl;

import com.user.management.app.exception.NoPermissionsException;
import com.user.management.app.exception.UserDataAlreadyExistException;
import com.user.management.app.model.entity.User;
import com.user.management.app.repository.TokenRepository;
import com.user.management.app.repository.UserRepository;
import com.user.management.app.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.user.management.app.constant.RolType.isAdmin;
import static com.user.management.app.constant.RolType.isAgent;

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

    private final TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Retrieve all registered user in system
     *
     * @return
     */
    @Override
    public List<User> getAll(String authorization) {
        Long rolId =tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser().getRol().getId();
        if(isAdmin(rolId) || isAgent(rolId)){
            return userRepository.findAll();
        }else {
            throw new NoPermissionsException("Usuario sin permisos para relaizar la operacion");
        }
    }

    /**
     * Retrieve user information
     *
     * @return
     */
    @Override
    public User get(String authorization) {
        return tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser();
    }

    /**
     * Update user information
     *
     * @param userInformation
     */
    @Override
    public User update(String authorization, User userInformation) {
        User authenticatedUser = tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser();
        if(authenticatedUser.getUserName().equals(userInformation.getUserName()) ||
            isAdmin(authenticatedUser.getRol().getId())) {

            User userToUpdate = userRepository.findFirstByUserName(userInformation.getUserName());
            userToUpdate.setEmail(userInformation.getEmail());
            userToUpdate.setName(userInformation.getName());
            userToUpdate.setPhoneNumber(userInformation.getPhoneNumber());
            return userRepository.save(userToUpdate);
        } else {
            throw new NoPermissionsException("Usuario sin permisos para relaizar la operacion");
        }
    }

    /**
     * Save new user
     *
     * @param newUser
     * @return
     */
    @Override
    public User save(User newUser) {
        if(!ObjectUtils.isEmpty(userRepository.findFirstByUserName(newUser.getUserName()))){
            throw new UserDataAlreadyExistException("User already exist in system");
        }
        return userRepository.save(newUser);
    }

    /**
     * Active user
     *
     * @param userToActive
     * @return
     */
    @Override
    public User active(User userToActive) {
        userToActive.setActivated(Boolean.TRUE);
        return userRepository.save(userToActive);
    }

    /**
     * Seve the given list of users
     *
     * @param users
     */
    @Override
    public void saveAll(List<User> users) {
        List<User> usersInSystem = userRepository.findAll();
        users = users.stream()
                .filter(user -> !usersInSystem.stream()
                        .anyMatch(user1 -> user1.getUserName().equals(user.getUserName())))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    /**
     * Retrieve user information by username
     *
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        return userRepository.findFirstByUserName(username);
    }

    /**
     * Update password for given username
     *
     * @param username
     * @param passwod
     * @return
     */
    @Override
    public User updatePassword(String username, String passwod) {
        User user = this.getByUsername(username);
        user.setPassword(passwod);
        return this.userRepository.save(user);
    }

}
