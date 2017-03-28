package com.oscar.jwt;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /* Maps to all HTTP actions by default (GET,POST,..)*/
    @RequestMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody String getUsers() {
        return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
    }
    
    
    @RequestMapping("/dummy1")
    public @ResponseBody String getDummy1() {
        return "{\"mydata\": \"Testing1\"}";
    }

    @RequestMapping("/dummy2")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody String getDummy2() {
        return "{\"mydata\": \"Testing2\"}";
    }

}
