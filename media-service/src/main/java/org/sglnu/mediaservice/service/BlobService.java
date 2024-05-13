package org.sglnu.mediaservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.mediaservice.configuration.properties.SasAccessProperties;
import org.sglnu.mediaservice.domain.datatype.InteractionType;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlobService {

    private final SasAccessProperties sasAccessProperties;
    private final BlobContainerClient blobContainerClient;

    public String generateAccessLink(String fileName, InteractionType permission) {
        BlobSasPermission sasPermission = BlobSasPermission.parse(permission.getStringRepresentation());

        BlobServiceSasSignatureValues sasValues =
                new BlobServiceSasSignatureValues(OffsetDateTime.now().plusSeconds(sasAccessProperties.getAccessDuration()), sasPermission)
                        .setProtocol(SasProtocol.HTTPS_ONLY)
                        .setCacheControl("no-cache");

        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        log.info("Started creating new sas link for blob {}", blobClient.getBlobName());

        return blobClient.getBlobUrl() + "?" + blobClient.generateSas(sasValues);
    }

    public void deleteFromStorage(String blobName) {
        blobContainerClient.getBlobClient(blobName).delete();
    }
}
