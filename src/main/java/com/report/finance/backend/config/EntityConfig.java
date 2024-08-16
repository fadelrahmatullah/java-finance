package com.report.finance.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.report.finance.backend.entity")
public class EntityConfig {
    
}
