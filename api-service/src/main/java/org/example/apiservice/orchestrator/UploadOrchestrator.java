package org.example.apiservice.orchestrator;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.dto.FileUploadEvent;
import org.example.apiservice.entity.FileEntity;
import org.example.apiservice.kafka.FileUploadEventProducer;
import org.example.apiservice.service.FileService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadOrchestrator {
    private final FileService service;
    private final FileUploadEventProducer producer;

    public FileEntity handleUpload(
            UUID fileId,
            Long clientId,
            String idempotencyKey,
            String tempPath
    ) {
        FileEntity entity = service.saveFileIfNotExist(
                fileId,
                clientId,
                idempotencyKey,
                tempPath
        );

        producer.send(new FileUploadEvent(entity.getId(), tempPath));

        return entity;
    }
}
