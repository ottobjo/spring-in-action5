package com.cobsweden.learn.tacocloud.configuration;

import jakarta.servlet.http.HttpServlet;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebConfiguration {

  @Bean
  ServletRegistrationBean<HttpServlet> h2servletRegistration(){
    ServletRegistrationBean<HttpServlet> registrationBean = new ServletRegistrationBean<>( new JakartaWebServlet() );
    registrationBean.addUrlMappings("/console/*");
    return registrationBean;
  }
}
