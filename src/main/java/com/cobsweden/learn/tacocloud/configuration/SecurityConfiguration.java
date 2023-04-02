package com.cobsweden.learn.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
class SecurityConfiguration {

  @Bean
  @SuppressWarnings("java:S1874") // is acceptable for demos and getting started
  UserDetailsManager users(DataSource dataSource) {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user")
        .password("password")
        .roles("USER")
        .build();
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.createUser(user);
    return users;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/design/**", "/order/**").authenticated()
            .requestMatchers("/images/**", "/styles.css").permitAll()
            .anyRequest().permitAll())         // test config, should be denyAll()
        .formLogin()

        .and()
        .csrf().disable()                      // test config allowing external access to H2 console
        .headers().frameOptions().disable()    //
    ;
    return http.build();
  }
}
