package io.marklove.spring.security.jwt.controllers;

import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exceptions.CommonException;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.error.BaseValidatedError;
import io.marklove.spring.security.jwt.utils.GetMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AdviceController {

    @Autowired
    private GetMessageServiceImpl messageService;

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTokenRefreshException(TokenRefreshException ex) {

        return new ErrorResponse(ex.getCode(),
                messageService.getMessage(ex.getCode()),
                null);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {

        return new ErrorResponse(AuthConstants.Error.BAD_CREDENTIALS,
                messageService.getMessage(AuthConstants.Error.BAD_CREDENTIALS),
                null);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidException(MethodArgumentNotValidException ex) {
        List<BaseValidatedError> errors = new ArrayList<>();
        for (ObjectError obE : ex.getAllErrors()) {
            if (obE instanceof FieldError) {
                FieldError fE = (FieldError) obE;
                errors.add(new BaseValidatedError(fE.getCode(), fE.getField(),
                        fE.getDefaultMessage()));
            }
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                messageService.getMessage(MessageCode.VALIDATED_ERROR), errors);
    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleCommonException(CommonException ex) {
        List<Object> args = ex.getArgs();
        String errorCode = ex.getCode();
        String message = messageService.getMessage(errorCode);

        if (args != null && !args.isEmpty()) {
            message = String.format(message, args);
        }

        return new ErrorResponse(errorCode, message, null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleException(Exception ex) {

        return new ErrorResponse(MessageCode.EXCEPTION,
                messageService.getMessage(MessageCode.EXCEPTION),
                ex.getMessage());
    }
}

