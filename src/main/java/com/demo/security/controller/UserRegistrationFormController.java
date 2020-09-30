package com.demo.security.controller;


import com.demo.security.model.Role;
import com.demo.security.model.User;
import com.demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserRegistrationFormController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("user") User user, RedirectAttributes redirAttrs){
        String pwd = user.getPassword();
        String encryptpwd = passwordEncoder.encode(pwd);
        user.setPassword(encryptpwd);
        Set<Role> roles= new HashSet<>();
        Role role=new Role();
        role.setRole("USER");
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        if(user!=null){
            redirAttrs.addFlashAttribute("success", "Registration done Successfully");
        }else{
            redirAttrs.addFlashAttribute("error", "Sorry! Somthing going wrong");
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "userRegistrationForm";
    }
}
