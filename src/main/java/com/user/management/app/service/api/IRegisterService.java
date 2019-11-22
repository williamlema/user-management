package com.user.management.app.service.api;

import com.user.management.app.model.entity.User;

import java.security.NoSuchAlgorithmException;

/**
 * register operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IRegisterService {
    /**
     * Create user and send email with verification token
     *
     * @param newUser
     */
    void registerNewUser(User newUser) throws NoSuchAlgorithmException;

    /**
     * Validate verification
     *
     * @param token
     */
    void validateUser(String token);
}
