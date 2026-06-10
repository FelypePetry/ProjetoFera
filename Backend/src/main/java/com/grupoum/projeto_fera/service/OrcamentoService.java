package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.*;
import com.grupoum.projeto_fera.repository.OrcamentoRepository;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImageUploadService imageUploadService;
    private final ProdutoRepository produtoRepository;

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

    public Orcamento criar(Orcamento orcamento, MultipartFile imagem) throws IOException {
        // 1. Trata a imagem normalmente
        String imagemPath = null;
        if (imagem != null && !imagem.isEmpty()) {
            imagemPath = imageUploadService.save(imagem, "orcamentos");
        }
        orcamento.setImagemPath(imagemPath);

        // 2. Processa os produtos selecionados
        if (orcamento.getProdutosSelecionadosIds() != null) {
            for (Long produtoId : orcamento.getProdutosSelecionadosIds()) {
                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                // Instancia a classe intermediária que o log mencionou
                OrcamentoProduto item = new OrcamentoProduto();
                item.setProduto(produto);
                item.setOrcamento(orcamento);
                item.setQuantidade(1); // caso tenha campos extras

                orcamento.getOrcamentoProdutos().add(item);
            }
        }

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
