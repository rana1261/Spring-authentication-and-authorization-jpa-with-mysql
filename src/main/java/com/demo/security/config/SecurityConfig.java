package com.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserDetailsService userDetailsService;


	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*Bellow code use for In-memory Authentication.
		 it use to just verify or authenticate the user.
		 akane ata chalou korle viewProfile page error dekabe ar
		 karon inMemory te username ar password ache onno data nai tai.jemon(email,role,etc)*/

		/*auth.inMemoryAuthentication()
				.withUser("safi").password(encodePWD().encode("safi123"))
				.roles("WONER");
				.and()
				.withUser("kafi").password(encodePWD().encode("kafi123"))
				.roles("ADMIN","USER");*/


		/*ekane database teke data ke call kora hoi
		userdetailsService interface er loadUserByUsername(String username) method ke implementaion kore
		ekane je username pelam sete login page er username er sate check kore page authenticate or verify kore
        deke ami ki sei  real login kora user kina.*/

		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());

		/*login page ami je username and password dilam
		seta login korar por by default muche fele.
		login korar por jeno muche na fele tar jonno eraseCredentials method er parameter false korte hobe.
		ata korar karon viewProfilController class e akta method ache
		jeta rawPassword abong encyptPassword match kore boolean value return kore.
		jar maddome viwProfile page amra  rawPassword dekabo*/

		auth.eraseCredentials(false);
	}


	/*ekane atuentication er por authorize er kaj kora hoi
    mane kon page e ke dekte parbe ar ke dekte parbe na tar kaj ekane kora hoi */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http
				.authorizeRequests()
				.antMatchers("/registration", "/home", "/").permitAll()
				.antMatchers("/addRole").hasAnyRole("WONER","ADMIN")
				.antMatchers("/manager").hasAnyRole("MANAGER","WONER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/authHome")
			.and()
				.logout()
				.permitAll()
				.logoutSuccessUrl("/login?logout")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/403");


	}



}
