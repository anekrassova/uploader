package org.example.apiservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.apiservice.dto.FileUploadEvent;
import org.example.apiservice.entity.FileEntity;
import org.example.apiservice.entity.FileStatus;
import org.example.apiservice.kafka.FileUploadEventProducer;
import org.example.apiservice.repo.FileRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileUploadEventProducer producer;

    public FileEntity findByClientAndKey(Long clientId, String key) {
        return fileRepository
                .findByClientIdAndIdempotencyKey(clientId, key)
                .orElse(null);
    }

    @Transactional
    public FileEntity saveFileIfNotExist(UUID fileId, Long clientId, String key, String tempPath) {
        return fileRepository
                .findByClientIdAndIdempotencyKey(clientId, key)
                .orElseGet(() -> {

                    FileEntity entity = new FileEntity(
                            fileId,
                            clientId,
                            key,
                            FileStatus.UPLOADING,
                            tempPath,
                            Instant.now()
                    );

                    FileEntity saved = fileRepository.save(entity);

                    producer.send(new FileUploadEvent(fileId, tempPath));

                    return saved;
                });
    }
}
