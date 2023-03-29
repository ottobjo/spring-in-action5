package com.cobsweden.learn.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
public class SecurityConfiguration {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    var users = List.of(
        User.withDefaultPasswordEncoder().username("buzz").password("infinity").roles("USER").build(),
        User.withDefaultPasswordEncoder().username("woody").password("bullseye").roles("USER").build()
    );
    return new InMemoryUserDetailsManager(users);
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated()).formLogin();
    return http.build();
  }
}
