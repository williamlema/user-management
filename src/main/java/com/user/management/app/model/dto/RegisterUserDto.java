package com.user.management.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto implements Serializable {

    private String name;

    private String password;

    private String userName;

    private String email;

    private String phoneNumber;
}
