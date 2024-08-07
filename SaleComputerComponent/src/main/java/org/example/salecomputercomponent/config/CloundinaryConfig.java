package org.example.salecomputercomponent.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloundinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
            final Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "drjoyphxe");
            config.put("api_key", "837168631483714");
            config.put("api_secret", "dzOviq6U0wIDMDgCC6KFzJsEwZw");
            return new Cloudinary(config);
    };
}
