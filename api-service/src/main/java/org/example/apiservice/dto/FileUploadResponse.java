package org.example.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.apiservice.entity.FileStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadResponse {
    private UUID fileId;
    private FileStatus fileStatus;
}
