package com.demo.security.controller;


import com.demo.security.model.Role;
import com.demo.security.model.User;
import com.demo.security.repository.RoleRepository;
import com.demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class RoleManagementController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/AddUserRoles")
    public String roleControllerAddUserRole(Model model, @RequestParam("userid") int userid, @RequestParam("role") String role, RedirectAttributes redirAttrs){


        User user=userRepository.findById(userid).get();

        Role role2=new Role();
        role2.setRole(role);
        roleRepository.save(role2);

        user.getRoles().add(role2);
        userRepository.save(user);

        if(role2!=null){
            redirAttrs.addFlashAttribute("success", "Role  add Successfully");
        }else{
            redirAttrs.addFlashAttribute("error", "Sorry! Somthing going wrong");
        }

        return "redirect:/addRole";
    }

    @GetMapping("/addRole")
    public String addRole(Model model){

        List<String> listRole = Arrays.asList("MANAGER", "ADMIN");
        model.addAttribute("listRole", listRole);

        return "roleManagementController";
    }
}
