package org.example.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadEvent {
    private UUID fileId;
    private String tempPath;
}
