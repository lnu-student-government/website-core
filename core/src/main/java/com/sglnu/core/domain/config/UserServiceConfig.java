package com.sglnu.core.domain.config;

import com.sglnu.userservice.mapper.UserMapper;
import com.sglnu.userservice.mapper.UserMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.sglnu.userservice")
public class UserServiceConfig {

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

}
