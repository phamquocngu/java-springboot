/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.marklove.oauth2token.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "login")
public class LoginConfig {
    public int maxLoginAttempt;
    public int loginAttempPeriod;
    public int lockTimeAfterLoginFail;

    public int getMaxLoginAttempt() {
        return maxLoginAttempt;
    }

    public void setMaxLoginAttempt(int maxLoginAttempt) {
        this.maxLoginAttempt = maxLoginAttempt;
    }

    public int getLoginAttempPeriod() {
        return loginAttempPeriod;
    }

    public void setLoginAttempPeriod(int loginAttempPeriod) {
        this.loginAttempPeriod = loginAttempPeriod;
    }

    public int getLockTimeAfterLoginFail() {
        return lockTimeAfterLoginFail;
    }

    public void setLockTimeAfterLoginFail(int lockTimeAfterLoginFail) {
        this.lockTimeAfterLoginFail = lockTimeAfterLoginFail;
    }
}
