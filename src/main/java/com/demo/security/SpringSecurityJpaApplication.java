package com.demo.security;

import com.demo.security.model.Role;
import com.demo.security.model.User;
import com.demo.security.repository.RoleRepository;
import com.demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityJpaApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJpaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		int id=1;
		User user=null;
		try {
			 user=userRepository.findById(id).get();
			if(user!=null){
				userRepository.save(user);
			}
		}catch (Exception ex){
			user=new User("sohel",passwordEncoder.encode("sohel123"),"rana1261@gmail.com");
			Set<Role> roles= new HashSet<>();
			Role role=new Role("WONER");
			roles.add(role);
			user.setRoles(roles);
			userRepository.save(user);
		}

	}
}
