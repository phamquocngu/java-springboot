package io.marklove.springboot.social.model.converters;

import io.marklove.springboot.social.constants.enums.AuthProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AuthProviderConverter implements AttributeConverter<AuthProvider, Byte> {
    @Override
    public Byte convertToDatabaseColumn(AuthProvider type) {
        if (type == null)
            return null;
        return type.getCode();
    }

    @Override
    public AuthProvider convertToEntityAttribute(Byte code) {
        if (code == null)
            return null;

        return AuthProvider.valueOf(code);
    }
}
