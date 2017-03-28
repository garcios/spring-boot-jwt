package com.oscar.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
import com.oscar.jwt.dao.UserDao;
import com.oscar.jwt.service.UserService;
import com.oscar.jwt.model.User;
 
@Service
public class UserService implements UserDetailsService {
 
    @Autowired
    private UserDao userDao;
 
    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }
}
