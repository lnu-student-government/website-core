package org.sglnu.mediaservice.service;

import org.sglnu.mediaservice.configuration.properties.FileDeletionProperties.DeletionProperties;
import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.RequestContext;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import org.sglnu.mediaservice.dto.FileRequest;
import org.sglnu.mediaservice.dto.FileResponse;
import org.sglnu.mediaservice.exception.DetailedEntityNotFoundException;
import org.sglnu.mediaservice.mapper.FileMapper;
import org.sglnu.mediaservice.repository.FileRepository;
import com.querydsl.core.types.Predicate;
import org.sglnu.mediaservice.domain.datatype.InteractionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileService {

    private final DeletionProperties deletionProperties;
    private final RequestContext requestContext;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse save(FileRequest fileRequest) {
        log.info("User[id={}] requested write access SAS link", fileRequest.getOwnerId());

        File savedFile = fileRepository.save(fileMapper.toFile(fileRequest));

        log.info("File[id={}] database entry successfully created", savedFile.getId());

        return fileMapper.toFileResponse(savedFile, InteractionType.WRITE);
    }

    @Transactional
    public Page<FileResponse> getAll(Predicate filters, Pageable pageable) {
        log.info("User[ip={}] requested batch read access SAS link", requestContext.getIp());

        return fileRepository.findAll(filters, pageable).map(file -> fileMapper.toFileResponse(file, InteractionType.READ));
    }

    @Transactional
    public FileResponse getById(Long fileId) {
        log.info("Authenticated[{}] User[ip={} | id={}] requested read access SAS link for File[id={}]",
                false, requestContext.getIp(), null, fileId);

        return fileRepository.findById(fileId)
                .map(file -> fileMapper.toFileResponse(file, InteractionType.READ))
                .orElseThrow(() -> new DetailedEntityNotFoundException("Entity of id [%s] couldn't be found".formatted(fileId), "File", fileId));
    }

    @Transactional
    public void deleteById(Long fileId) {
        log.info("Deletion on file[id={}] has been requested!", fileId);
        fileRepository.findById(fileId).ifPresent(this::requestDeletion);
    }

    @Transactional
    public void deleteByTarget(TargetType targetType, Long targetId) {
        log.info("Deletion on File[targetType={}, targetId={}] has been requested!", targetType, targetId);
        fileRepository.findAllByTargetTypeAndTargetId(targetType, targetId).forEach(this::requestDeletion);
    }

    private void requestDeletion(File file) {
        file.requestDeletion(deletionProperties.deleteAfterDuration());
        fileRepository.save(file);
    }
}
