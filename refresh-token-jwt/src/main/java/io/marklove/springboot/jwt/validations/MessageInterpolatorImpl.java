package io.marklove.springboot.jwt.validations;

import io.marklove.springboot.jwt.utils.GetMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.MessageInterpolator;
import java.util.Locale;

/**
 * @author ngupq
 */
public class MessageInterpolatorImpl implements MessageInterpolator {
    @Autowired
    private final MessageInterpolator defaultInterpolator;
    @Autowired
    private GetMessageService getMessageService;

    public MessageInterpolatorImpl(MessageInterpolator interpolator) {
        this.defaultInterpolator = interpolator;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        messageTemplate = getMessageService.getMessage(messageTemplate);
        return defaultInterpolator.interpolate(messageTemplate, context);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        messageTemplate = getMessageService.getMessage(messageTemplate);
        return defaultInterpolator.interpolate(messageTemplate, context, locale);
    }
}
