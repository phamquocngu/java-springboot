package io.marklove.spring.security.jwt.advice;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.exception.BusinessException;
import io.marklove.spring.security.jwt.exception.ValidatedException;
import io.marklove.spring.security.jwt.payload.response.ErrorResponse;
import io.marklove.spring.security.jwt.payload.response.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.payload.response.base.BaseValidatedError;
import io.marklove.spring.security.jwt.utils.MessageServiceImpl;
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
public class ControllerAdvice {

    @Autowired
    private MessageServiceImpl messageService;

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleBusinessException(BusinessException ex, WebRequest request) {
        return new ErrorResponse(
                ex.getCode(),
                messageService.getMessage(ex.getCode()),
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

