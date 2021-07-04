package io.marklove.spring.security.jwt.payload.response;

import io.marklove.spring.security.jwt.constants.Constants;
import io.marklove.spring.security.jwt.payload.response.base.UserInfor;

import java.time.LocalDateTime;

public class JwtResponse {
    private String token;
    private String type = Constants.BEARER;
    private String refreshToken;
    private UserInfor userInfor;
    private LocalDateTime dateTime;

    public JwtResponse(String accessToken, String refreshToken, UserInfor userInfor) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.userInfor = userInfor;
        this.dateTime = LocalDateTime.now();
    }

    public JwtResponse(String accessToken, String refreshToken, UserInfor userInfor, LocalDateTime dateTime) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.userInfor = userInfor;
        this.dateTime = dateTime;
    }

    public String getAccessToken() {
        return token;
    }

    public String getTokenType() {
        return type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public UserInfor getUserInfor() {
        return userInfor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
