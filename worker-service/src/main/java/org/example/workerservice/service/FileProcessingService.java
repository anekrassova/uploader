package org.example.workerservice.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.workerservice.dto.FileUploadEvent;
import org.example.workerservice.entity.FileEntity;
import org.example.workerservice.entity.FileStatus;
import org.example.workerservice.repo.FileRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FileProcessingService {
    private final FileRepository fileRepository;
    private final MinioClient minioClient;

    @Transactional
    public void process(FileUploadEvent event) {
        FileEntity file = fileRepository.findById(event.getFileId())
                .orElseThrow();

        if (file.getStatus() != FileStatus.UPLOADING) {
            return;
        }

        Path path = Path.of(event.getTempPath());

        try(InputStream is = Files.newInputStream(path)) {
            String objectName = path.getFileName().toString();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("files")
                            .object(objectName)
                            .stream(is, Files.size(path), -1)
                            .contentType("application/octet-stream")
                            .build()
            );

            file.setStatus(FileStatus.DONE);
            file.setStoragePath("files/" + objectName);
            file.setUpdatedAt(Instant.now());

            fileRepository.save(file);

            Files.deleteIfExists(path);
        }catch (Exception e){
            file.setStatus(FileStatus.FAILED);
            file.setUpdatedAt(Instant.now());
            throw new RuntimeException(e);
        }
    }
}
