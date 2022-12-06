package com.contentplusplus.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain secFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests()
		.requestMatchers("/register/**").permitAll()
		.requestMatchers("/listuser/**").hasAnyAuthority("ADMIN", "EDITOR", "VIEWER")//.hasAuthority("ADMIN")//.hasRole("ROLE_ADMIN")//.hasAuthority("ROLE_ADMIN")
		.requestMatchers("/login").permitAll()
		.requestMatchers("/signup/**").permitAll()
		.requestMatchers("/spring.svg/**").permitAll()
		.requestMatchers("/webjars/**").permitAll()
		.requestMatchers("/img/**").permitAll()
		.requestMatchers("/css/**").permitAll()
		.requestMatchers("/js/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll().defaultSuccessUrl("/index")
		.and()
		.logout().permitAll()
		.and()
		.csrf().disable().exceptionHandling().accessDeniedPage("/403");

		http.headers().frameOptions().sameOrigin();

		return http.build();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}