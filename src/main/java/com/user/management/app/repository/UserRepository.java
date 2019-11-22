package com.user.management.app.repository;

import com.user.management.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to handle {@link User} entity in database
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieve User searched by userName
     *
     * @param userName
     * @return
     */
    User findFirstByUserName(String userName);
}
