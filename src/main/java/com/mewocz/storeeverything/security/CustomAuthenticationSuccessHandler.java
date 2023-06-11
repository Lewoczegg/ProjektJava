package com.mewocz.storeeverything.security;

import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.services.InformationService;
import com.mewocz.storeeverything.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    UserService userService;
    InformationService informationService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService, InformationService informationService) {
        this.userService = userService;
        this.informationService = informationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.findUserByLogin(email);
        System.out.println(user.getRoles());
        if (informationService.getInformationWithTodaysReminderDate(user.getId()).size() != 0) {
            response.sendRedirect("/info/forToday"); // Redirect to admin dashboard if the user has the role "ROLE_ADMIN"
        }
        else if(Objects.equals(user.getRoles().get(0).toString(), "ROLE_LUSER")){
            response.sendRedirect("/info/shared");
        }
        else {
            response.sendRedirect("/info/"); // Redirect to user dashboard for other authenticated users
        }
    }

}