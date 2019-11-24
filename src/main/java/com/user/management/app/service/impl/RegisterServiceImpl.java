package com.user.management.app.service.impl;

import com.user.management.app.constant.TokenType;
import com.user.management.app.exception.InActiveTokenException;
import com.user.management.app.model.dto.RegisterUserDto;
import com.user.management.app.model.entity.Token;
import com.user.management.app.model.entity.User;
import com.user.management.app.repository.TokenRepository;
import com.user.management.app.service.api.IEmailService;
import com.user.management.app.service.api.IRegisterService;
import com.user.management.app.service.api.IRolService;
import com.user.management.app.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.user.management.app.util.TokenUtil.generateSafeToken;

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

    private final IRolService rolService;

    private final TokenRepository tokenRepository;

    private final Long DEFAULT_USER_ROL_ID = Long.valueOf(3);

    private final String SUBJECT = "Verificacion de cuenta";

    @Value( "${validate.address.url}" )
    private String VALIDATE_URL;

    @Autowired
    public RegisterServiceImpl(IEmailService emailService, IUserService userService, IRolService rolService, TokenRepository tokenRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.rolService = rolService;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Create user and send email with verification token
     *
     * @param newUser
     */
    @Override
    public void registerNewUser(RegisterUserDto newUser) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .name(newUser.getName())
                .userName(newUser.getUserName())
                .password(encoder.encode(newUser.getPassword()))
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .rol(rolService.get(DEFAULT_USER_ROL_ID))
                .activated(Boolean.FALSE)
                .build();

        User savedUser = userService.save(user);
        Token temporalToken = Token.builder()
                .token(generateSafeToken())
                .active(Boolean.TRUE)
                .type(TokenType.REGISTER.name())
                .user(savedUser)
                .build();
        Token generatedToken = tokenRepository.save(temporalToken);
        String message ="Abrir el siguiente link en el nevagador \n "
                .concat(VALIDATE_URL).concat(generatedToken.getToken());
        emailService.sendMail(newUser.getEmail(), SUBJECT, message);
    }

    /**
     * Validate verification
     *
     * @param token
     */
    @Override
    public void validateUser(String token) {
        Token savedToken = tokenRepository.findFirstByTokenAndActive(token, Boolean.TRUE);
        if(ObjectUtils.isEmpty(savedToken)){
            throw new InActiveTokenException("Token inactivo");
        }
        userService.active(savedToken.getUser());
        savedToken.setActive(Boolean.FALSE);
        tokenRepository.save(savedToken);
    }
}
