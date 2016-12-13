package com.epam.rd.backend.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("tmp-data")
@Configuration
@ComponentScan("com.epam.rd.backend.core.populator")
public class TemporaryDataPopulationConfig {
}
