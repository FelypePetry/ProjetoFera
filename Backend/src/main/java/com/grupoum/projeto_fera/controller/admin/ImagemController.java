package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/imagens")
@RequiredArgsConstructor
public class ImagemController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = imageUploadService.save(file, "produtos");
        return ResponseEntity.ok(Map.of("url", filename));
    }
}
