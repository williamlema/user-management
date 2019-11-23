package com.user.management.app.service.api;

import com.user.management.app.model.dto.RegisterUser;

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
    void registerNewUser(RegisterUser newUser);

    /**
     * Validate verification
     *
     * @param token
     */
    void validateUser(String token);
}
