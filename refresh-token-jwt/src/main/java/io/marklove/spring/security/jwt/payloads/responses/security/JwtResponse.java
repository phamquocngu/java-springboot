package io.marklove.spring.security.jwt.payloads.responses.security;

import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.payloads.responses.base.UserInfor;

import java.time.LocalDateTime;

public class JwtResponse {
    private String accessToken;
    private String type = AuthConstants.BEARER;
    private String refreshToken;
    private UserInfor userInfor;
    private LocalDateTime dateTime;

    public JwtResponse(String accessToken, String refreshToken, UserInfor userInfor) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfor = userInfor;
        this.dateTime = LocalDateTime.now();
    }

    public JwtResponse(String accessToken, String refreshToken, UserInfor userInfor, LocalDateTime dateTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfor = userInfor;
        this.dateTime = dateTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getType() {
        return type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserInfor getUserInfor() {
        return userInfor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
