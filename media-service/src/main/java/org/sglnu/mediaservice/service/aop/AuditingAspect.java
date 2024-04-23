package org.sglnu.mediaservice.service.aop;

import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.FileAudit;
import org.sglnu.mediaservice.domain.RequestContext;
import org.sglnu.mediaservice.domain.datatype.InteractionType;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import org.sglnu.mediaservice.dto.FileResponse;
import org.sglnu.mediaservice.repository.FileAuditRepository;
import org.sglnu.mediaservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditingAspect {

    private final FileRepository fileRepository;
    private final FileAuditRepository fileAuditRepository;
    private final RequestContext requestContext;

    @AfterReturning(
            value = "execution(public org.sglnu.mediaservice.dto.FileResponse save(org.sglnu.mediaservice.dto.FileRequest))",
            argNames = "fileResponse",
            returning = "fileResponse")
    public void saveFileAdvice(FileResponse fileResponse) {
        addAuditEntry(fileResponse.getId(), InteractionType.WRITE, requestContext);
    }

    @AfterReturning(
            value = "execution(public org.sglnu.mediaservice.dto.FileResponse getById(Long)) && args(fileId)",
            argNames = "fileId")
    public void getByIdAdvice(Long fileId) {
        addAuditEntry(fileId, InteractionType.READ, requestContext);
    }

    @AfterReturning(
            value = "execution(public org.springframework.data.domain.Page getAll(..))",
            argNames = "filePage",
            returning = "filePage")
    public void getAllAdvice(Page<FileResponse> filePage) {
        filePage.forEach(fileResponse -> addAuditEntry(fileResponse.getId(), InteractionType.READ, requestContext));
    }

    @AfterReturning(
            value = "execution(public void deleteById(Long)) && args(fileId)",
            argNames = "fileId")
    public void deleteByIdAdvice(long fileId) {
        addAuditEntry(fileId, InteractionType.DELETE, requestContext);
    }

    @AfterReturning(
            value = "execution(public void deleteByTarget(..)) && args(targetType, targetId)",
            argNames = "targetType,targetId")
    public void deleteByTarget(TargetType targetType, Long targetId) {
        fileRepository.findAllByTargetTypeAndTargetId(targetType, targetId)
                .forEach(file -> addAuditEntry(file.getId(), InteractionType.DELETE, requestContext));
    }

    private void addAuditEntry(Long fileId, InteractionType interactionType, RequestContext requestContext) {
        File actualFile = fileRepository.getReferenceById(fileId);
        FileAudit fileAudit = new FileAudit();
        fileAudit.setUserId(actualFile.getOwnerId());
        fileAudit.setUserIp(requestContext.getIp());
        fileAudit.setInteractionType(interactionType);

        fileAudit.setFile(actualFile);
        actualFile.addFileAuditEntry(fileAudit);
        fileAuditRepository.save(fileAudit);
        fileRepository.save(actualFile);
    }
}
