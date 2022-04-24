package io.marklove.springboot.social.controller;

import io.marklove.springboot.social.exception.ResourceNotFoundException;
import io.marklove.springboot.social.model.User;
import io.marklove.springboot.social.repository.UserRepository;
import io.marklove.springboot.social.security.CurrentUser;
import io.marklove.springboot.social.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
