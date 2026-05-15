package com.projectGroupWD55.bikeRentalAndRideSharing.controller;

import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserLogin;
import com.projectGroupWD55.bikeRentalAndRideSharing.dto.UserResponse;
import com.projectGroupWD55.bikeRentalAndRideSharing.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserWebController {

    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userLogin") UserLogin userLogin, HttpSession session) {
        try {
            UserResponse response = userService.verifyUser(userLogin);
            session.setAttribute("loggedInUser", response);

            if ("ADMIN".equals(response.getRole())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";  // redirect to index.html instead of /rides

        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }
    @GetMapping("/user/home")
    public String home(HttpSession session, Model model) {
        UserResponse user = (UserResponse) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        return "index";
    }

    // --- REGISTRATION ---

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userLogin") UserLogin userLogin) {
        try {
            userService.registerUser(userLogin);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            return "redirect:/register?error=true";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}