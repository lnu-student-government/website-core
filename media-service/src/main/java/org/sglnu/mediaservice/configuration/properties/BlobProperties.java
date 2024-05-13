package org.sglnu.mediaservice.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.cloud.azure.storage.blob")
public class BlobProperties {

    private String accountName;
    private String accountKey;
    private String endpoint;
    private String containerName;

    public String connectionString() {
        return String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=%s", accountName, accountKey, endpoint);
    }

}
