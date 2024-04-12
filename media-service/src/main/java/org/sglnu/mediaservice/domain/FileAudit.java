package org.sglnu.mediaservice.domain;

import org.sglnu.mediaservice.domain.datatype.InteractionType;
import org.sglnu.mediaservice.validator.annotation.IpV4;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "file_audit")
@EntityListeners(AuditingEntityListener.class)
public class FileAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Column(updatable = false)
    private Long userId;

    @IpV4
    private String userIp;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InteractionType interactionType;

    @CreatedDate
    private LocalDateTime interactionTime;

    @ManyToOne
    private File file;
}
