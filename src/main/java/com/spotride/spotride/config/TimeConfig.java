package com.spotride.spotride.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * Configures application to work in UTC timezone.
 */
@Component
public class TimeConfig {

    /**
     * Obtains a clock that returns the current instant using the best available system clock,
     * converting to date and time using the UTC time-zone.
     * @return Clock instance
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    /**
     * Sets default application timezone to UTC.
     */
    @PostConstruct
    public void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

}
