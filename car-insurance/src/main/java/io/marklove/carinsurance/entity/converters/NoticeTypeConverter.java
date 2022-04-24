package io.marklove.carinsurance.entity.converters;

import io.marklove.carinsurance.constant.enums.ENoticeType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NoticeTypeConverter implements AttributeConverter<ENoticeType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ENoticeType status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public ENoticeType convertToEntityAttribute(Integer code) {
        return code != null ? ENoticeType.valueOf(code) : null;
    }
}
