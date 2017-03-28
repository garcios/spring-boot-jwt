package com.oscar.jwt.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.oscar.jwt.model.User;
import com.oscar.jwt.service.UserService;
 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	 
	@Autowired
	private UserService userService;
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
         

        // use the credentials
        // and authenticate against the third-party system
        Collection<? extends GrantedAuthority> authorities = shouldAuthenticateAgainstThirdPartySystem(name, password);
    
        if ( authorities !=null) {
  
            return new UsernamePasswordAuthenticationToken(
              name, password, authorities);
        } else {
            return null;
        }
    }
 
    private Collection<? extends GrantedAuthority> shouldAuthenticateAgainstThirdPartySystem(String name, String password) {
		
    	
    	User user = userService.loadUserByUsername(name);
    	
    	
    	if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }
    	
    	if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
    	
    	
		return user.getAuthorities();
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}
