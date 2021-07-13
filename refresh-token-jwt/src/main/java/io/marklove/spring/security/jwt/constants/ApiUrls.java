package io.marklove.spring.security.jwt.constants;

/**
 * @author ngupq
 */
public class ApiUrls {
    // security APIs
    public static final String SIGN_IN = "/api/auth/sign-in";
    public static final String REFRESH_TOKEN = "/api/auth/refresh-token";
    public static final String LOG_OUT = "/api/user/log-out";
    public static final String CREATE_USER = "/api/admin/create-user";
    public static final String SIGN_UP_REGISTER = "/api/sign-up/register";
    public static final String SIGN_UP_VERIFY = "/api/sign-up/verify";
    public static final String RESET_PASS_REQUEST = "/api/reset-pass/request";
    public static final String RESET_PASS_VERIFY = "/api/reset-pass/verify";
}
