package com.bluejeanssystems.backlog.form;

import com.bluejeanssystems.backlog.util.Authority;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    @NotBlank
    private String username;

    private String password;

    private String confirmPassword;

    private Authority authority;

    private Long teamId;
}
