package com.spotride.spotride.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 *  H2 in-memory database is used for integration tests.This context initializer sets UTC timezone before
 *  any actions to make H2 and application use the same timezone. H2uses the current time zone setting of the JVM.
 *  It is not possible to pass timezone config in any other way. It isalso not possible to change timezone value
 *  after JDBC driver was loaded. Doc: http://www.h2database.com/html/tutorial.html#date_time
 */
public class H2TimeZoneContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

}
