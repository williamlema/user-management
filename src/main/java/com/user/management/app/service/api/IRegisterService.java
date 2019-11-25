package com.user.management.app.service.api;

import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.RegisterUserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    void registerNewUser(RegisterUserDto newUser);

    /**
     * Do verification over user
     *
     * @param token
     */
    void verificationUser(String token);

    /**
     * Created multiple user listed in file
     * @param userFile
     */
    void bulkRegister(String authorization, MultipartFile userFile) throws IOException;

    /**
     * Validate if user complete registration process
     * @param username
     */
    Integer validateUser(String username);

    /**
     * Complete user registration
     *
     * @param credentialDto
     */
    void completeRegister(CredentialDto credentialDto);
}
