package com.user.management.app.service.impl;

import com.user.management.app.model.entity.User;
import com.user.management.app.repository.RolRepository;
import com.user.management.app.service.api.IEmailService;
import com.user.management.app.service.api.IRegisterService;
import com.user.management.app.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Implementation class for register operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class RegisterServiceImpl implements IRegisterService {

    private final IEmailService emailService;

    private final IUserService userService;

    private final RolRepository rolRepository;

    private final Long DEFAULT_USER_ROL_ID = Long.valueOf(1);

    private SecureRandom random;

    @Autowired
    public RegisterServiceImpl(IEmailService emailService, IUserService userService, RolRepository rolRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.rolRepository = rolRepository;
    }

    /**
     * Create user and send email with verification token
     *
     * @param newUser
     */
    @Override
    public void registerNewUser(User newUser) throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstanceStrong();
        newUser.setRol(rolRepository.getOne(DEFAULT_USER_ROL_ID));
        User savedUser = userService.save(newUser);

    }

    /**
     * Validate verification
     *
     * @param token
     */
    @Override
    public void validateUser(String token) {


    }
}
