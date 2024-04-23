package org.sglnu.mediaservice.domain.datatype;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TargetType {

    USER("users/%d/"),
    EVENT("events/%d/");

    private final String folderPrefix;

    public String getPrefixForId(Long id) {
        return folderPrefix.formatted(id);
    }
}
