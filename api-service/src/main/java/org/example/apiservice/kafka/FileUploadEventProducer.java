package org.example.apiservice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.dto.FileUploadEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileUploadEventProducer {
    private final KafkaTemplate<String, FileUploadEvent> kafkaTemplate;

    public void send(FileUploadEvent event) {
        kafkaTemplate.send("topic-1", event);
    }
}
