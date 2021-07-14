package io.marklove.spring.security.jwt.controllers;

import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.exceptions.BusinessException;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.exceptions.ValidatedException;
import io.marklove.spring.security.jwt.payloads.responses.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.base.BaseValidatedError;
import io.marklove.spring.security.jwt.utils.GetMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AdviceController {

    @Autowired
    private GetMessageServiceImpl messageService;

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {

        return new ErrorResponse(ex.getCode(),
                messageService.getMessage(ex.getCode()),
                request.getDescription(false));
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleBusinessException(BusinessException ex, WebRequest request) {
        List<Object> args = ex.getArgs();
        String errorCode = ex.getCode();
        String message = messageService.getMessage(errorCode);

        if (args != null && !args.isEmpty()) {
            message = String.format(message, args);
        }

        return new ErrorResponse(errorCode, message, request.getDescription(false));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleException(Exception ex, WebRequest request) {

        return new ErrorResponse(MessageCode.EXCEPTION,
                messageService.getMessage(MessageCode.EXCEPTION),
                request.getDescription(false));
    }

    @ExceptionHandler(value = ValidatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidatedErrorResponse handleValidatorException(ValidatedException ex) {
        List<BaseValidatedError> errors = new ArrayList<>();
        for (ObjectError obE : ex.getErrors().getAllErrors()) {
            if (obE instanceof FieldError) {
                FieldError fE = (FieldError) obE;
                errors.add(new BaseValidatedError(fE.getDefaultMessage(), fE.getField(),
                        messageService.getMessage(fE.getDefaultMessage())));
            }
        }

        return new ValidatedErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                messageService.getMessage(ValidationCode.VALIDATED_ERROR), errors);
    }
}

