package io.marklove.springboot.jwt.config;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CusAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date());
        map.put("status", 403);
        map.put("message", "Access denied");
        map.put("path", request.getRequestURI());
        response.getWriter().write(new Gson().toJson(map));
    }
}
