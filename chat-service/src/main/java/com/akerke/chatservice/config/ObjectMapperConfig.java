package com.akerke.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Configuration
public class ObjectMapperConfig {

    @Bean
    ObjectMapper objectMapper(){
        var mapper = new ObjectMapper();

        var dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(dateFormat);

        mapper.configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES,false);
        return mapper;
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(BytesToLocalDateTimeConverter bytesToLocalDateTimeConverter) {
        return new RedisCustomConversions(List.of(bytesToLocalDateTimeConverter));

    }

    @Component
    @ReadingConverter
    public static class BytesToLocalDateTimeConverter implements Converter<byte[], LocalDateTime> {

        @Override
        public LocalDateTime convert(final byte[] source) {
            return LocalDateTime.parse(new String(source), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
