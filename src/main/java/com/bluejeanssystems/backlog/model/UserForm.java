package com.bluejeanssystems.backlog.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private String password;

    private String confirmPassword;
}
