package io.marklove.spring.security.jwt.payload.response;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author ngupq
 */
public class UnAuthResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime dateTime;

    public UnAuthResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
