package com.epam.rd.frontend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(value = {"com.epam.rd.frontend.component",
        "com.epam.rd.frontend.service",
        "com.epam.rd.frontend.converter"})
@Import({WebConfig.class,
        SecurityConfig.class})
public class SpringConfig {

}
