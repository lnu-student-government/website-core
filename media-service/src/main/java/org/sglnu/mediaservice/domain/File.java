package org.sglnu.mediaservice.domain;

import org.sglnu.mediaservice.converter.MimeTypeConverter;
import org.sglnu.mediaservice.domain.datatype.Status;
import org.sglnu.mediaservice.domain.datatype.TargetType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.MimeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(updatable = false)
    private Long ownerId;

    @Enumerated(STRING)
    private Status status = Status.PRESENT;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDate deletionDate;

    @Convert(converter = MimeTypeConverter.class)
    private MimeType type;

    @Enumerated(STRING)
    private TargetType targetType;

    private Long targetId;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "file", fetch = FetchType.LAZY)
    private List<FileAudit> fileAudit = new ArrayList<>();

    public void addFileAuditEntry(FileAudit entry) {
        this.fileAudit.add(entry);
    }

    public void requestDeletion(long deleteAfterDuration) {
        this.setStatus(Status.DELETION_REQUESTED);
        this.setDeletionDate(LocalDate.now().plusDays(deleteAfterDuration));
    }
}
