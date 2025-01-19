package com.example.clinic.recipe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "email")
@Getter
@Setter
public class ConfigEmail {
    private String sender;
    private String host;
    private Integer port;
    private String password;
    private Map<String, String> titles;
}