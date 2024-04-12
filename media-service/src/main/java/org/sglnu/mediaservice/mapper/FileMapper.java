package org.sglnu.mediaservice.mapper;

import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.datatype.InteractionType;
import org.sglnu.mediaservice.dto.FileAccessLink;
import org.sglnu.mediaservice.dto.FileRequest;
import org.sglnu.mediaservice.dto.FileResponse;
import org.sglnu.mediaservice.service.BlobService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Mapper(componentModel = "spring")
public abstract class FileMapper {

    @Autowired
    private BlobService blobService;

    public abstract FileResponse toFileResponse(File file, InteractionType interactionType);

    public abstract File toFile(FileRequest fileRequest);

    @AfterMapping
    protected void setFileAccessLink(@MappingTarget FileResponse fileResponse, InteractionType interactionType) {
        fileResponse.setFileAccessLink(
                new FileAccessLink(interactionType, blobService.generateAccessLink(fileResponse.getName(), interactionType))
        );
    }

    @AfterMapping
    protected void setFileName(@MappingTarget File file, FileRequest fileRequest) {
        file.setName(generateFileName(generateFolderName(fileRequest), file.getType().getSubtype()));
    }

    private String generateFolderName(FileRequest fileRequest) {
        return fileRequest.getTargetType().getPrefixForId(fileRequest.getTargetId());
    }

    private String generateFileName(String folderName, String fileExtension) {
        return folderName + UUID.randomUUID() + ".%s".formatted(fileExtension);
    }
}
