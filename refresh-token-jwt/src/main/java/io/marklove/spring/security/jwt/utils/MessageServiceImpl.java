package io.marklove.spring.security.jwt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author ngupq
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ServletRequest servletRequest;

    @Override
    public String getMessage(String code) {
        try {
            return messageSource.getMessage(code, null, servletRequest.getLocale());
        } catch (Exception e) {
            logger.error("message service can't get message with code {}, Locale {}: ", code, servletRequest.getLocale());
            return "";
        }
    }
}
