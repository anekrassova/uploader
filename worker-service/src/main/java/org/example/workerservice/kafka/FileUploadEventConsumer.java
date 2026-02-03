package org.example.workerservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.workerservice.dto.FileUploadEvent;
import org.example.workerservice.service.FileProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.Acknowledgment;

@Component
@RequiredArgsConstructor
public class FileUploadEventConsumer {
    private final FileProcessingService service;

    @KafkaListener(topics="topic-1")
    public void consume(FileUploadEvent event, Acknowledgment ack) {
        service.process(event);

        ack.acknowledge();
    }
}
