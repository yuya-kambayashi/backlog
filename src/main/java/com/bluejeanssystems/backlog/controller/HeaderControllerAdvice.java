package com.bluejeanssystems.backlog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class HeaderControllerAdvice {

    @ModelAttribute("username")
    public String username(Authentication loginUser) {
        if (loginUser != null) {
            return loginUser.getName();
        }

        return "Default username";
    }
}
