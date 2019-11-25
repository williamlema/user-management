package com.user.management.app.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity class to retrieve User information from database
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "rol")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol implements Serializable {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="description")
    private String description;

    @Column(name="update_permission")
    private boolean updatePermission;

    @Column(name="read_own_data")
    private boolean readOwnData;

    @Column(name="edit_own_data")
    private boolean editOwnData;

    @Column(name="read_other_user_data")
    private boolean readOtherUserData;

    @Column(name="edit_other_user_data")
    private boolean editOtherUserData;

}
