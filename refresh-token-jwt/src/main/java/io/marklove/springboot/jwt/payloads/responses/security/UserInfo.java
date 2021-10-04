package io.marklove.springboot.jwt.payloads.responses.security;

import lombok.Getter;

import java.util.Set;

/**
 * @author ngupq
 */
@Getter
public class UserInfo {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;

    public UserInfo(Long id, String username, String email, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
