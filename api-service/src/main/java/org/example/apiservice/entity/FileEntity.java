package org.example.apiservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="files", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_id", "idempotency_key"})
})
@Getter
@Setter
@NoArgsConstructor
public class FileEntity {
    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status;

    @Column(name = "temp_path")
    private String tempPath;

    @Column(name = "storage_path")
    private String storagePath;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public FileEntity(
            UUID id,
            Long clientId,
            String idempotencyKey,
            FileStatus status,
            String tempPath,
            Instant createdAt
    ) {
        this.id = id;
        this.clientId = clientId;
        this.idempotencyKey = idempotencyKey;
        this.status = status;
        this.tempPath = tempPath;
        this.createdAt = createdAt;
    }

}
