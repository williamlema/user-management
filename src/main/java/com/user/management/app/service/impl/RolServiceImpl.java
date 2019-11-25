package com.user.management.app.service.impl;

import com.user.management.app.exception.NoPermissionsException;
import com.user.management.app.model.entity.Rol;
import com.user.management.app.repository.RolRepository;
import com.user.management.app.repository.TokenRepository;
import com.user.management.app.service.api.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.user.management.app.constant.RolType.isAdmin;

/**
 * Implementation class for CRUD operation for rol entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class RolServiceImpl implements IRolService {

    private final RolRepository rolRepository;

    private final TokenRepository tokenRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, TokenRepository tokenRepository) {
        this.rolRepository = rolRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Retrieve all rol configured in system
     *
     * @param authorization
     * @return
     */
    @Override
    public List<Rol> getAll(String authorization) {
        if(isAdmin(tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser().getRol().getId())){
            return rolRepository.findAll();
        } else {
            throw new NoPermissionsException("Usuario sin permisos para relaizar la operacion");
        }
    }

    /**
     * Update rol information
     *
     * @param authorization
     * @param rolInformation
     */
    @Override
    public Rol update(String authorization, Rol rolInformation) {
        if(isAdmin(tokenRepository.findFirstByTokenAndActive(authorization, Boolean.TRUE).getUser().getRol().getId())){
            return rolRepository.save(rolInformation);
        } else {
            throw new NoPermissionsException("Usuario sin permisos para relaizar la operacion");
        }
    }

    /**
     * Get rol information
     *
     * @param id
     */
    @Override
    public Rol get(Long id) {
        return rolRepository.getOne(id);
    }
}
