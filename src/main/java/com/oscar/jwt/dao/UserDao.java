package com.oscar.jwt.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.oscar.jwt.model.Role;
import com.oscar.jwt.model.User;
 
@Repository
public class UserDao {
 
    public User loadUserByUsername(final String username) {
    	
    	if (!username.equals("osgarcia") && !username.equals("dummyuser1")){
    		return null;
    	}
    	
        User user = new User();
        List<Role> roles = new ArrayList<Role>();
        
        if (username.equals("osgarcia")){
            user.setFirstName("Oscar");
            user.setLastName("Garcia");
            user.setUsername("osgarcia");
            user.setPassword("password123");
            
            Role r1 = new Role();
            r1.setName("ADMIN");

            Role r2 = new Role();
            r2.setName("USER");

            roles.add(r1);
            roles.add(r2);
            
        }
 
        if (username.equals("dummyuser1")){
            user.setFirstName("dummy");
            user.setLastName("user1");
            user.setUsername("dummyuser");
            user.setPassword("password888");
            
            Role r1 = new Role();
            r1.setName("USER");

            roles.add(r1);
            
        }
        
        
        
        
         user.setAuthorities(roles);
        
        
         return user;
    }
}
