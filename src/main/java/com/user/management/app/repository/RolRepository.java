package com.user.management.app.repository;

import com.user.management.app.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to handle {@link Rol} entity in database
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

}
