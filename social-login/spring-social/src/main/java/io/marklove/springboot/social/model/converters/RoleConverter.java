package io.marklove.springboot.social.model.converters;

import io.marklove.springboot.social.constants.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Byte> {
    @Override
    public Byte convertToDatabaseColumn(Role type) {
        if (type == null)
            return null;
        return type.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Byte code) {
        if (code == null)
            return null;

        return Role.valueOf(code);
    }
}
