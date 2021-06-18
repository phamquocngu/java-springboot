package io.marklove.springboot.jwt.controller;

import io.marklove.springboot.jwt.config.JwtTokenProvider;
import io.marklove.springboot.jwt.model.CustomUserDetails;
import io.marklove.springboot.jwt.payload.LoginRequest;
import io.marklove.springboot.jwt.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class JwtController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        //Authentication from username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        //If no exception occurs, the information is valid
        //Set authentication to Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Return jwt.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    //Api require authenticate
    @GetMapping("/random")
    public String random(){
        return "Authentication success!!!";
    }
}
