package com.user.management.app.service.api;

import com.user.management.app.model.entity.Rol;

import java.util.List;

/**
 * CRUD operation for rol entity
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface IRolService {

    /**
     * Retrieve all rol configured in system
     *
     * @return
     */
    List<Rol> getAll(String authorization);

    /**
     *
     * Update rol information
     */
    Rol update(String authorization, Rol rolInformation);

    /**
     *
     * Get rol information
     */
    Rol get(Long id);
}
