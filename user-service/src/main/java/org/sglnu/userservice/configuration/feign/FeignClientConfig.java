package org.sglnu.userservice.configuration.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public FeignErrorDecoder decoder() {
        return new FeignErrorDecoder();
    }

}
