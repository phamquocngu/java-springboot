package io.marklove.springboot.jwt.payloads.responses.security;

import io.marklove.springboot.jwt.constants.AuthConstants;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ngupq
 */
@Getter
public class JwtRes implements Serializable {
    private final String accessToken;
    private final String type = AuthConstants.BEARER;
    private final String refreshToken;
    private final UserInfo userInfo;
    private LocalDateTime dateTime;

    public JwtRes(String accessToken, String refreshToken, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfo = userInfo;
        this.dateTime = LocalDateTime.now();
    }

    public JwtRes(String accessToken, String refreshToken, UserInfo userInfo, LocalDateTime dateTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfo = userInfo;
        this.dateTime = dateTime;
    }
}
