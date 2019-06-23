package com.driver.car.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApiSecurity extends WebSecurityConfigurerAdapter {

	// Create 2 users for demo
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("USER").and().withUser("admin")
				.password("{noop}admin123").roles("USER", "ADMIN");

	}

	// Secure the endpoints with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		  httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
          .authorizeRequests().antMatchers("/h2-console/**").permitAll();

		httpSecurity.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/v1/drivers/**").hasRole("USER")
			.antMatchers(HttpMethod.GET,"/v1/cars/**").hasRole("USER")
			.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
			.antMatchers(HttpMethod.POST).hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT).hasRole("ADMIN")
	       .and().httpBasic();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}

}
