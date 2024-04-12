package org.sglnu.mediaservice.dto;

import org.sglnu.mediaservice.domain.datatype.Status;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MimeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private Long id;
    private String name;
    private Status status;
    private Long ownerId;
    private String type;
    private LocalDateTime createdAt;
    private TargetType targetType;
    private Long targetId;
    private FileAccessLink fileAccessLink;

    public void setType(MimeType mimeType) {
        this.type = mimeType.toString();
    }
}
