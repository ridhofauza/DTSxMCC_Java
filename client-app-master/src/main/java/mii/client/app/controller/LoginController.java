/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import mii.client.app.model.dto.request.LoginRequest;
import mii.client.app.service.LoginService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MSI-JO
 */
@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @GetMapping
    public String loginView(LoginRequest loginRequest, Model model, Authentication authentication) {
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "auth/login"; 
        }
        
        return "redirect:/dashboard";
    }

    @PostMapping
    public String login(LoginRequest loginRequest) {
        if (!loginService.login(loginRequest)) {
            return "redirect:/login?error=true";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities()
                .stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        
        for (String role : roles) {
            if (role.equals("ROLE_USER")) {
                return "redirect:/region";
            }
            if (role.equals("ROLE_ADMIN")) {
                return "redirect:/country";
            }
        }

        return "redirect:/dashboard";
    }

}
