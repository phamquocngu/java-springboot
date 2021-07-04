package io.marklove.spring.security.jwt.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ngupq
 */
public interface MessageService {
    String getMessage(String code);
}
