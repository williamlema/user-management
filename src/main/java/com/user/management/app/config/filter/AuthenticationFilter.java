package com.user.management.app.config.filter;

import com.user.management.app.exception.NoAuthenticatedException;
import com.user.management.app.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorization =  httpRequest.getHeader("Authorization");
        if(ObjectUtils.isEmpty(authorization)
                || ObjectUtils.isEmpty(tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE))){
            throw new NoAuthenticatedException("Usuario no autenticado");
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
