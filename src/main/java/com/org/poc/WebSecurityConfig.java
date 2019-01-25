package com.org.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PropertiesConfiguration properties;
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
    	// Reading users and passwords from properties file
    	String[] users = properties.getUsers();
    	String[] passwords = properties.getPasswords();
    	
    	String user1 = "default1";
    	String password1 = "default1";
    	String user2 ="default2";
    	String password2 = "default2";
      
    	if(users.length>0 && passwords.length>0) {
        	user1 = users[0];
        	user2=users[1];
        	password1=passwords[0];
        	password2=passwords[1];
        }
    	
    	 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
         auth.inMemoryAuthentication()
           .withUser(user1)
           .password(encoder.encode(password1))
           .roles("USER")
           .and()
           .withUser(user2)
           .password(encoder.encode(password2))
           .roles("USER");

    }
}