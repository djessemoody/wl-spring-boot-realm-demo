package com.moodycode.security;//package com.swampfox.security;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.j2ee.WebXmlMappableAttributesRetriever;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Created by moody on 7/18/17.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  MappableAttributesRetriever webXmlRolesParser;

  @Autowired
  Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper;

  @Autowired
  AuthenticationUserDetailsService getUserDetailsService;

  @Autowired
  AuthenticationProvider preAuthenticationProvider;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> authDetailsSource;




  @Bean
  @ConditionalOnMissingBean(RequestContextListener.class)
  public RequestContextListener requestContextListener() {

    return new RequestContextListener();
  }


  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new Http403ForbiddenEntryPoint();
  }

  @Bean
  public MappableAttributesRetriever webXmlRolesParser() {
    return new WebXmlMappableAttributesRetriever();
  }

  @Bean
  public Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper() {
    SimpleAttributes2GrantedAuthoritiesMapper var = new SimpleAttributes2GrantedAuthoritiesMapper();
    var.setAttributePrefix("");
    return var;
  }

  @Bean
  public AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> authDetailsSource() {
    J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource var = new J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource();
    var.setMappableRolesRetriever(this.webXmlRolesParser);
    var.setUserRoles2GrantedAuthoritiesMapper(this.roles2GrantedAuthoritiesMapper);
    return var;
  }

  @Bean
  public AuthenticationUserDetailsService getUserDetailsService() {
    return new PreAuthorizationUserDetailsService() ;
  }

  @Bean
  public AuthenticationProvider preAuthenticationProvider() {
    PreAuthenticatedAuthenticationProvider var = new PreAuthenticatedAuthenticationProvider();
    var.setPreAuthenticatedUserDetailsService(this.getUserDetailsService);
    return var;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(Arrays.asList(this.preAuthenticationProvider));
  }


  @Bean
  public AbstractPreAuthenticatedProcessingFilter preAuthFilter() {
    J2eePreAuthenticatedProcessingFilter var = new J2eePreAuthenticatedProcessingFilter();
    var.setAuthenticationManager(this.authenticationManager);
    var.setAuthenticationDetailsSource(this.authDetailsSource);
    return var;
  }




  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.addFilterAfter(preAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests().antMatchers("/public/**","/login*","/").permitAll().anyRequest().authenticated();
    http.csrf().disable();
  }





}
