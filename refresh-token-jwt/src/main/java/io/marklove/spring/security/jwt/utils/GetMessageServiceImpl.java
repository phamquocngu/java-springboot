package io.marklove.spring.security.jwt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 * @author ngupq
 */
@Service
public class GetMessageServiceImpl implements GetMessageService {
    private static final Logger logger = LoggerFactory.getLogger(GetMessageServiceImpl.class);

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
