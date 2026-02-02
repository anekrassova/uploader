package org.example.apiservice.repo;

import org.example.apiservice.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    Optional<FileEntity> findByClientIdAndIdempotencyKey(Long clientId, String idempotencyKey);
}
