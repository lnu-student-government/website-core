package org.sglnu.mediaservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.MimeType;

@Converter
public class MimeTypeConverter implements AttributeConverter<MimeType, String> {

    @Override
    public String convertToDatabaseColumn(MimeType attribute) {
        return attribute.toString();
    }

    @Override
    public MimeType convertToEntityAttribute(String dbData) {
        return MimeType.valueOf(dbData);
    }
}
