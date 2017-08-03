package com.moodycode.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

/**
 * Created by moody on 7/28/17.
 */
public class PreAuthorizationUserDetailsService implements AuthenticationUserDetailsService
{
  private final Logger log = LoggerFactory.getLogger(PreAuthorizationUserDetailsService.class);


  @Override
  public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException
  {
    PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails tokenDetails =
        (PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) token.getDetails();



    return new User(
        (String) token.getPrincipal(),
        "unused", true, true, true, true,
        tokenDetails.getGrantedAuthorities());
  }

}
