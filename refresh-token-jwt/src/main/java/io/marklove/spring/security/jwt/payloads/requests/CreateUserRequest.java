package io.marklove.spring.security.jwt.payloads.requests;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import java.util.Set;

import javax.validation.constraints.*;
 
public class CreateUserRequest {
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 4, max = 20, message = ValidationCode.VALIDATED_SIZE)
    private String username;
 
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Email(message = ValidationCode.VALIDATED_EMAIL)
    @Size(max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String email;
    
    private Set<String> role;
    
    @NotBlank(message = ValidationCode.VALIDATED_BLANK)
    @Size(min = 6, max = 50, message = ValidationCode.VALIDATED_SIZE)
    private String password;
    private Boolean enable;
    private Boolean accountLocked;
    private Boolean accountExpired;
    private Boolean credentialsExpired;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
}
