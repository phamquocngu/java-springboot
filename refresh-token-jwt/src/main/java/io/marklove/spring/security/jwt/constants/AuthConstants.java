package io.marklove.spring.security.jwt.constants;

/**
 * @author ngupq
 */
public class AuthConstants {
    public static final String BEARER = "Bearer";
    public class Error {
        public static final String EXPIRED_JWT = "expired-jwt";
        public static final String UNAUTHORIZED = "unauthorized";
        public static final  String EXPIRED_REFRESH_TOKEN = "expired-refresh-token";
        public static final  String FAILED_GENERATE_TOKEN = "failed-generate-token";
    }

}
