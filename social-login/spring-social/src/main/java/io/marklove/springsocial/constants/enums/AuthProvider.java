package io.marklove.springsocial.constants.enums;

public enum AuthProvider {
    local(Value.LOCAL),
    facebook(Value.FACEBOOK),
    google(Value.GOOGLE),
    github(Value.GITHUB);

    private final byte code;

    AuthProvider(final byte code) {
        this.code = code;
    }

    public final byte getCode() {
        return this.code;
    }

    public static AuthProvider valueOf(byte code) {
        for (AuthProvider type : AuthProvider.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }

        throw new IllegalArgumentException(AuthProvider.class.getName() + " does not have value with code: " + code);
    }

    public static class Value {
        public static final byte LOCAL = 0;
        public static final byte FACEBOOK = 1;
        public static final byte GOOGLE = 2;
        public static final byte GITHUB = 3;
    }
}
