package com.user.management.app.service.api;

import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.TokenDto;

/**
 * Authentication operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IAuthenticationService {

    /**
     * Given user credentials try to do login in system
     * @param credentials
     * @return
     */
    TokenDto login(CredentialDto credentials);

    /**
     *
     * @param token
     */
    void logout(String token);
}
