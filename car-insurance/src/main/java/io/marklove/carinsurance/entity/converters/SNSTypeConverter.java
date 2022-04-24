package io.marklove.carinsurance.entity.converters;

import io.marklove.carinsurance.constant.enums.ESNSType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SNSTypeConverter implements AttributeConverter<ESNSType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ESNSType type) {
        if (type == null)
            return null;
        return type.getCode();
    }

    @Override
    public ESNSType convertToEntityAttribute(Integer code) {
        if (code == null)
            return null;

        return ESNSType.valueOf(code);
    }
}
