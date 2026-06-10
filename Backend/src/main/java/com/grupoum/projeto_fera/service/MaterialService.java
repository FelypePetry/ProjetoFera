package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Material;
import com.grupoum.projeto_fera.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public Material save(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public Optional<Material> findById(Long id) {
        return materialRepository.findById(id);
    }

    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }
}
