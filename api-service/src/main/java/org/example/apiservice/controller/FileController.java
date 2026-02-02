package org.example.apiservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.dto.FileUploadResponse;
import org.example.apiservice.entity.FileEntity;
import org.example.apiservice.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestParam("clientId") Long clientId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        UUID fileId = UUID.randomUUID();
        String tempFileName = fileId + "_" + file.getOriginalFilename();
        Path tempPath = Path.of("/tmp/uploads/", tempFileName);

        Files.createDirectories(tempPath.getParent());
        Files.write(tempPath, file.getBytes());

        FileEntity entity = fileService.saveFileIfNotExist(fileId, clientId, idempotencyKey, tempPath.toString());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new FileUploadResponse(entity.getId(), entity.getStatus()));
    }
}
