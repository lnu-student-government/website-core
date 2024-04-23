package org.sglnu.mediaservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AzureProperties.SasAccessProperties.class, AzureProperties.BlobProperties.class})
public class AzureProperties {

    @ConfigurationProperties(prefix = "cloud.blob.sas")
    public record SasAccessProperties(long accessDuration) {
    }

    @ConfigurationProperties(prefix = "cloud.azure.storage.blob")
    public record BlobProperties(String accountName, String accountKey, String endpoint, String containerName) {

        public String connectionString() {
            return String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=%s", accountName, accountKey, endpoint);
        }

    }

}
