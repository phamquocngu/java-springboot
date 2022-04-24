package io.marklove.carinsurance.entity.converters;

import io.marklove.carinsurance.constant.enums.ERegistrationStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProcessingStatusConverter implements AttributeConverter<ERegistrationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ERegistrationStatus type) {
        if (type == null)
            return null;

        return type.getCode();
    }

    @Override
    public ERegistrationStatus convertToEntityAttribute(Integer code) {
        if (code == null)
            return null;

        return ERegistrationStatus.valueOf(code);
    }
}
