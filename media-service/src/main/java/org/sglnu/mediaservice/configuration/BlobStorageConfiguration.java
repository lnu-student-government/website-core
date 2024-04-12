package org.sglnu.mediaservice.configuration;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import org.sglnu.mediaservice.configuration.properties.AzureProperties.BlobProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BlobStorageConfiguration {

    private final BlobProperties blobProperties;

    @Bean
    public BlobContainerClient blobContainerClient() {
        return new BlobContainerClientBuilder()
                .connectionString(blobProperties.connectionString())
                .containerName(blobProperties.containerName())
                .buildClient();
    }

}
