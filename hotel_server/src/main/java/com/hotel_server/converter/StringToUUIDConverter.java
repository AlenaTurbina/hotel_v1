package com.hotel_server.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
final class StringToUUIDConverter implements Converter<String, UUID> {

    @Override
    @Nullable
    public UUID convert(String source) {
        return (StringUtils.hasText(source) ? UUID.fromString(source.trim()) : null);
    }
}
