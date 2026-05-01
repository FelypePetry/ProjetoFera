package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Orcamento;
import com.grupoum.projeto_fera.model.StatusOrcamento;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.OrcamentoRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Orcamento> listarTodos() {
        return orcamentoRepository.findAllByOrderByCriadoEmDesc();
    }

    @Transactional(readOnly = true)
    public Orcamento buscarPorId(Long id) {
        return orcamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<Orcamento> listarPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return orcamentoRepository.findByUsuario(usuario);
    }

    @Transactional(readOnly = true)
    public long contarPorStatus(StatusOrcamento status) {
        return orcamentoRepository.countByStatus(status);
    }

    public Orcamento criar(String tipoMovel, String medidas, String observacoes,
                           MultipartFile imagem, String emailUsuario) throws IOException {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String imagemPath = null;
        if (imagem != null && !imagem.isEmpty()) {
            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path destino = Paths.get("uploads/" + nomeArquivo);
            Files.createDirectories(destino.getParent());
            Files.copy(imagem.getInputStream(), destino);
            imagemPath = nomeArquivo;
        }

        Orcamento orcamento = Orcamento.builder()
                .tipoMovel(tipoMovel)
                .medidas(medidas)
                .observacoes(observacoes)
                .imagemPath(imagemPath)
                .usuario(usuario)
                .build();

        return orcamentoRepository.save(orcamento);
    }

    public Orcamento atualizarStatus(Long id, StatusOrcamento novoStatus) {
        Orcamento orcamento = buscarPorId(id);
        orcamento.setStatus(novoStatus);
        return orcamentoRepository.save(orcamento);
    }

    public void deletar(Long id) {
        orcamentoRepository.delete(buscarPorId(id));
    }
}
