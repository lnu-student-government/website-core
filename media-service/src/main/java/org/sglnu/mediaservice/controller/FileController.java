package org.sglnu.mediaservice.controller;

import org.sglnu.mediaservice.domain.File;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import org.sglnu.mediaservice.dto.FileRequest;
import org.sglnu.mediaservice.dto.FileResponse;
import org.sglnu.mediaservice.service.FileService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    @ResponseStatus(CREATED)
    public FileResponse save(@RequestBody @Valid FileRequest fileRequest) {
        return fileService.save(fileRequest);
    }

    @GetMapping
    public Page<FileResponse> getAll(@QuerydslPredicate(root = File.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return fileService.getAll(filters, pageable);
    }

    @GetMapping(path = "/{fileId}")
    public FileResponse getById(@PathVariable Long fileId) {
        return fileService.getById(fileId);
    }

    @DeleteMapping("/{fileId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long fileId) {
        fileService.deleteById(fileId);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteByTarget(@RequestParam(name = "targetType") TargetType targetType,
                               @RequestParam(name = "targetId") Long targetId) {
        fileService.deleteByTarget(targetType, targetId);
    }
}
