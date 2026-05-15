package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Cor;
import com.grupoum.projeto_fera.repository.CorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CorService {

    private final CorRepository corRepository;

    public Cor save(Cor cor) {
        return corRepository.save(cor);
    }

    public List<Cor> findAll() {
        return corRepository.findAll();
    }

    public Optional<Cor> findById(Long id) {
        return corRepository.findById(id);
    }

    public void deleteById(Long id) {
        corRepository.deleteById(id);
    }
}
