/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.marklove.oauth2token.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.marklove.oauth2token.config.LoginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private LoadingCache<String, Integer> attemptsCache;
    private LoadingCache<String, Integer> listIpBlockCache;

    @Autowired
    LoginConfig loginConfig;

    @PostConstruct
    public void init() {
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(loginConfig.getLoginAttempPeriod(), TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        });
        listIpBlockCache = CacheBuilder.newBuilder().
                expireAfterWrite(loginConfig.getLockTimeAfterLoginFail(), TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key)  {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
        listIpBlockCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (Exception e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
        if(attempts == loginConfig.getMaxLoginAttempt())
            listIpBlockCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return listIpBlockCache.get(key) >= loginConfig.getMaxLoginAttempt();
        } catch (ExecutionException e) {
            return false;
        }
    }
}
