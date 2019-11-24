package com.user.management.app.service.impl;

import com.user.management.app.constant.TokenType;
import com.user.management.app.exception.CredentialException;
import com.user.management.app.exception.InActiveUserException;
import com.user.management.app.model.dto.CredentialDto;
import com.user.management.app.model.dto.TokenDto;
import com.user.management.app.model.entity.Token;
import com.user.management.app.model.entity.User;
import com.user.management.app.repository.TokenRepository;
import com.user.management.app.repository.UserRepository;
import com.user.management.app.service.api.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.user.management.app.util.TokenUtil.generateSafeToken;

/**
 * Implementation class for authentication operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Given user credentials try to do login in system
     *
     * @param credentials
     * @return
     */
    @Override
    public TokenDto login(CredentialDto credentials) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findFirstByUserName(credentials.getUsername());

        if (ObjectUtils.isEmpty(user) || !encoder.matches(credentials.getPassword(),user.getPassword())){
            throw new CredentialException("Usuario o password incorrecto");
        }

        if(!user.isActivated()){
            throw new InActiveUserException("El usuario no se encuentra activado");
        }

        List<Token> tokenList = tokenRepository.findAllByUserAndActive(user, Boolean.TRUE);
        tokenList.stream().forEach(token -> token.setActive(Boolean.FALSE));
        tokenRepository.saveAll(tokenList);

        Token token = tokenRepository.save(Token.builder()
                .token(generateSafeToken())
                .active(Boolean.TRUE)
                .type(TokenType.AUTH.name())
                .user(user)
                .build());
        return TokenDto.builder().token(token.getToken()).build();
    }

    /**
     * @param token
     */
    @Override
    public void logout(String token) {
        Token t = tokenRepository.findFirstByTokenAndActive(token, Boolean.TRUE);
        t.setActive(Boolean.FALSE);
        tokenRepository.save(t);
    }
}
