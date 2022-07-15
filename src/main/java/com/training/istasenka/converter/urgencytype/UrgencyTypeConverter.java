package com.training.istasenka.converter.urgencytype;

import com.training.istasenka.util.UrgencyType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UrgencyTypeConverter implements AttributeConverter<UrgencyType, String> {
    @Override
    public String convertToDatabaseColumn(UrgencyType urgencyType) {
        return urgencyType.getShortUrgencyTypeName();
    }

    @Override
    public UrgencyType convertToEntityAttribute(String s) {
        return UrgencyType.getUrgencyTypeName(s);
    }
}
