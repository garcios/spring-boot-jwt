package com.oscar.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.oscar.jwt.util.RoleParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;

/**
 * 
 * @author oscargarcia
 *
 */
class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 1000 * 60 * 60 ; // 1 hour
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    /**
     * 
     * @param res
     * @param auth
     */
    static void addAuthentication(HttpServletResponse res, Authentication auth) {
    	
    	Claims claims = Jwts.claims().setSubject(auth.getName());
    	claims.put("scopes", auth.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
    	
        String JWT = Jwts.builder()
        		.setClaims(claims)
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    /**
     * 
     * @param request
     * @return
     */
    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

              Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            
              System.out.println(">>>>>>>>>>>>");
              System.out.println(claims);
     
              System.out.println(claims.getExpiration());
              
            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
     		@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) claims.get("scopes");
     		if (roles!=null){
     	     	for (String role : roles) {
     	     	   System.out.println(role);	
     	     	   //System.out.println("RegEX:" + RoleParser.getRole(role));
     			   authorities.add(new SimpleGrantedAuthority(RoleParser.getRole(role)));
     		    }
     		}	
 
     		
     		org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
             
            return user != null ?
            		new UsernamePasswordAuthenticationToken(
             				principal, "", authorities) :
                    null;
        }
        return null;
    }
}
