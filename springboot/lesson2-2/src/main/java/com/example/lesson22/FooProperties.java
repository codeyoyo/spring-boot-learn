package com.example.lesson22;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.lesson")
public class FooProperties {

    private String foo;

    private String databasePlatform;
}
