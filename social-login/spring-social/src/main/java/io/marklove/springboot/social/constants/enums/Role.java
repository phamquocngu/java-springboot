package io.marklove.springboot.social.constants.enums;

public enum Role {
    ADMIN(Value.ADMIN),
    USER(Value.USER);

    private final byte code;

    Role(final byte code) {
        this.code = code;
    }

    public final byte getCode() {
        return this.code;
    }

    public static Role valueOf(byte code) {
        for (Role type : Role.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }

        throw new IllegalArgumentException(Role.class.getName() + " does not have value with code: " + code);
    }

    public static class Value {
        public static final byte ADMIN = 0;
        public static final byte USER = 1;
    }
}
