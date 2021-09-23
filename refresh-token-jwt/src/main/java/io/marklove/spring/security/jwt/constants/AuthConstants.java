package io.marklove.spring.security.jwt.constants;

/**
 * @author ngupq
 */
public class AuthConstants {
    public static final String BEARER = "Bearer";
    public class Error {
        public static final String INVALID_JWT_SIGNATURE = "invalid-jwt-signature";
        public static final String INVALID_JWT_TOKEN = "invalid-jwt-token";
        public static final String JWT_TOKEN_EXPIRED = "jwt-token-expired";
        public static final String JWT_TOKEN_UNSUPPORTED = "jwt-token-unsupported";
        public static final String JWT_CLAIMS_EMPTY = "jwt-claims-empty";
        public static final String UNAUTHORIZED = "unauthorized";
        public static final String EXPIRED_REFRESH_TOKEN = "expired-refresh-token";
        public static final String INVALID_REFRESH_TOKEN = "invalid-refresh-token";
        public static final String BAD_CREDENTIALS = "bad-credentials";
        public static final String GRANT_TYPE_NOT_SUPPORT = "grant-type-not-support";
    }
}
