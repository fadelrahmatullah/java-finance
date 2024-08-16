package com.report.finance.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.report.finance.backend.repository")
public class JpaConfig {
    
}
