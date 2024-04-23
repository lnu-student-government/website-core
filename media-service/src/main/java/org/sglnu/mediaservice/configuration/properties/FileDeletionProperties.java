package org.sglnu.mediaservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileDeletionProperties.DeletionProperties.class)
public class FileDeletionProperties {

    @ConfigurationProperties(prefix = "scheduling.file.operation.delete")
    public record DeletionProperties(long deleteAfterDuration) {
    }
}
