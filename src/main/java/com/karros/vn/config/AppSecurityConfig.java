package com.karros.vn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${karros.app.user.name}")
  private String userName;

  @Value("${karros.app.user.password}")
  private String password;
  
  @Value("${karros.app.admin.name}")
  private String adminName;

  @Value("${karros.app.admin.password}")
  private String adminPassword;
  
  // Create 2 users for demo
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
    .withUser(userName).password("{noop}" + password).roles("USER")
    .and()
    .withUser(adminName).password("{noop}" + adminPassword).roles("USER", "ADMIN");
  }

  // Secure the endpoins with HTTP Basic authentication
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    //HTTP Basic authentication
    .httpBasic()
    .and()
    .authorizeRequests()
    .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
    .antMatchers(HttpMethod.POST, "/user/**").hasRole("USER")
    .and()
    .csrf().disable()
    .formLogin().disable();
  }

  /*@Bean
    public UserDetailsService userDetailsService() {
        //ok for demo
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }*/

} 