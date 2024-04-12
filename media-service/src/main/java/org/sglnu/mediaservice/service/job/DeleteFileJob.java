package org.sglnu.mediaservice.service.job;

import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.QFile;
import org.sglnu.mediaservice.repository.FileRepository;
import org.sglnu.mediaservice.service.BlobService;
import org.sglnu.mediaservice.domain.datatype.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteFileJob {

    private final FileRepository fileRepository;
    private final BlobService blobService;

    @Scheduled(cron = "${scheduling.file.operation.delete.interval}")
    public void deleteRequestedFiles() {
        log.info("Scheduled delete operation invoked!");
        LocalDate currentDate = LocalDate.now();
        Page<File> deletedFiles;

        int page = 0;
        int pageSize = 20;

        do {
            deletedFiles = fileRepository.findAll(QFile.file.status.eq(Status.DELETION_REQUESTED), PageRequest.of(page++, pageSize));
            deletedFiles.stream()
                    .filter(file -> file.getDeletionDate().equals(currentDate))
                    .forEach(file -> {
                        fileRepository.delete(file);
                        blobService.deleteFromStorage(file.getName());
                    });
        } while (deletedFiles.hasNext());
    }
}
