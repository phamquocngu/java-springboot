package io.marklove.carinsurance.security;

import io.marklove.carinsurance.exception.BaseException;
import io.marklove.carinsurance.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SecurityException extends BaseException {

    public SecurityException(HttpStatus status, ErrorCode errorCode) {
        super(status, errorCode);
    }

    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SecurityException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public SecurityException(HttpStatus status, ErrorCode errorCode, String message) {
        super(status, errorCode, message);
    }

    public SecurityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
