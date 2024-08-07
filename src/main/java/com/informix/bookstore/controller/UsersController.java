package com.informix.bookstore.controller;

import com.informix.bookstore.entity.Users;
import com.informix.bookstore.entity.UsersType;
import com.informix.bookstore.service.UsersService;
import com.informix.bookstore.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {
    private final UsersService usersService;
    private final UsersTypeService usersTypeService;
    @Autowired
    public UsersController(UsersService usersService, UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String userRegister(@Valid Users user, Model model){
        Optional<Users> optionalUsers = usersService.getUserByEmail(user.getEmail());
        if(optionalUsers.isPresent()){
            model.addAttribute("error", "Email already registered. Try to login or register with other email.");
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(user);
        return "redirect:/dashboard/";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
