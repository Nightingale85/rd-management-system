package com.epam.rd.backend.web.config;

import com.google.common.collect.Lists;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.springmvc.controller.JSONDocController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
@PropertySource(value = "classpath:jsondoc.properties")
public class JsonDocConfig {
    @Value("${jsondoc.version}")
    private String docVersion;

    @Value("${jsondoc.basepath}")
    private String basePath;

    @Value("${jsondoc.package.controller}")
    private String controllerPackage;

    @Value("${jsondoc.package.dto}")
    private String controllerDto;

    @Bean
    public JSONDocController jsonDocController() {
        List<String> packages = Lists.newArrayList(controllerPackage, controllerDto);
        JSONDocController jsonDocController = new JSONDocController(docVersion, basePath, packages);
        jsonDocController.setDisplayMethodAs(JSONDoc.MethodDisplay.METHOD);
        return jsonDocController;
    }
}
