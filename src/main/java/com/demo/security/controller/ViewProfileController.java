package com.demo.security.controller;

import com.demo.security.model.User;
import com.demo.security.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class ViewProfileController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/viewProfile")
    public String viewProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user =customUserDetails.getUser();

       /* Role guloke koma(,) diye separete kore string akare prokash korar jonno nicher code ti kora hoiche.
        jemon [ADMIN,USER]----->ADMIN,USER avabe dekabe.*/
        String rolelist=user.getRoles()
                .stream()
                .map(role -> role.getRole().toString())
                .collect(Collectors.joining(" , "));

        /*akane login page er Rawpassword pabo. ar ai rawPassword pabar jonno SecurityConfig class er
        configure(AuthenticationManagerBuilder auth) method er vitor ata    "auth.eraseCredentials(false);"
         add korte hobe */
        String loginPageRawPassword= SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        /* akane row password er sate database encrypt password check kora hoi.
        ture hole rawPassword viewProfile page pathano hoi */
        System.out.println(loginPageRawPassword);
        boolean status=passwordEncoder.matches(loginPageRawPassword,user.getPassword());
        String decryptPassword =null;
        if(status){
            decryptPassword=loginPageRawPassword;
        }

        model.addAttribute("user",user);
        model.addAttribute("rolelist",rolelist);
        model.addAttribute("decryptPassword",decryptPassword);
        return "viewProfile";
    }
}
