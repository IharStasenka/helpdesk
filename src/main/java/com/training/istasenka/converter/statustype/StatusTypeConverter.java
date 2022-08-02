package com.training.istasenka.converter.statustype;

import com.training.istasenka.util.StatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusTypeConverter implements AttributeConverter<StatusType, String> {
    @Override
    public String convertToDatabaseColumn(StatusType statusType) {
        return statusType.getShortStatusTypeName();
    }

    @Override
    public StatusType convertToEntityAttribute(String s) {
        return StatusType.getFullStatusTypeName(s);
    }
}
