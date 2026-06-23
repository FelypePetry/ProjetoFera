package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.Cor;
import com.grupoum.projeto_fera.service.CorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cores")
@RequiredArgsConstructor
public class CorController {

    private final CorService corService;

    @PostMapping
    public Cor createCor(@RequestBody Cor cor) {
        return corService.save(cor);
    }

    @GetMapping
    public List<Cor> getAllCores() {
        return corService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCor(@PathVariable Long id) {
        corService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
