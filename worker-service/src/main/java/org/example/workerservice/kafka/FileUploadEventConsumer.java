package org.example.workerservice.kafka;

import org.example.workerservice.dto.FileUploadEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FileUploadEventConsumer {
    @KafkaListener(topics="topic-1")
    public void consume(FileUploadEvent event) {
        System.out.println("Received file upload event: {}"+ event);
    }
}
