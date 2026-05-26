package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.ImagemProd;
import com.grupoum.projeto_fera.repository.ImagemProdRepository;
import com.grupoum.projeto_fera.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/imagens")
@RequiredArgsConstructor
public class ImagemController {

    private final ImageUploadService imageUploadService;
    private final ImagemProdRepository imagemProdRepository;

    @PostMapping("/upload")
    public ResponseEntity<ImagemProd> uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = imageUploadService.save(file, "produtos");
        ImagemProd imagemProd = new ImagemProd();
        imagemProd.setUrlImagem(filename);
        ImagemProd savedImage = imagemProdRepository.save(imagemProd);
        return ResponseEntity.ok(savedImage);
    }
}
