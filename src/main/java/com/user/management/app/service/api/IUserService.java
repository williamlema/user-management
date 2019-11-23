package com.user.management.app.service.api;

import com.user.management.app.model.entity.User;

import java.util.List;

/**
 * CRUD operation for user entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IUserService {

    /**
     * Retrieve all registered user in system
     *
     * @return
     */
    List<User> getAll(String authorization);

    /**
     * Retrieve user information
     *
     * @return
     */
    User get(String authorization);

    /**
     *
     * Update user information
     */
    User update(String authorization, User userInformation);

    /**
     * Save new user
     *
     * @param newUser
     * @return
     */
    User save (User newUser);

    /**
     * Active user
     *
     * @param userToActive
     * @return
     */
    User active(User userToActive);
}
