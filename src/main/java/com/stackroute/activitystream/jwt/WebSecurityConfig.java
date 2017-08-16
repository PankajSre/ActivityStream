package com.stackroute.activitystream.jwt;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/user/login").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new JWTLoginFilter("/api/user/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select emailId , password, isActive from user where emailId=?")
				.authoritiesByUsernameQuery("select u1.emailId , u2.roleName from user u1 , Roles u2 where u1.emailId=u2.emailId and u1.emailId=?");
	*/
		 auth.inMemoryAuthentication()
	        .withUser("admin")
	        .password("password")
	        .roles("ADMIN");	
	}
}