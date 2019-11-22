package com.user.management.app.service.impl;

import com.user.management.app.model.entity.Rol;
import com.user.management.app.repository.RolRepository;
import com.user.management.app.service.api.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Retrieve all rol configured in system
     *
     * @param authorization
     * @return
     */
    @Override
    public List<Rol> getAll(String authorization) {
        return rolRepository.findAll();
    }

    /**
     * Update rol information
     *
     * @param authorization
     * @param rolInformation
     */
    @Override
    public Rol update(String authorization, Rol rolInformation) {
        return rolRepository.save(rolInformation);
    }
}
