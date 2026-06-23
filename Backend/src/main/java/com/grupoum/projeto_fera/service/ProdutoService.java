package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Cor;
import com.grupoum.projeto_fera.model.Material;
import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ImageUploadService imageUploadService;

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com código: " + codigo));
    }

    public Produto criar(Produto produto, List<MultipartFile> arquivos) {
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            throw new RuntimeException("Código já cadastrado: " + produto.getCodigo());
        }
        Set<Material> materiais = new HashSet<>(produto.getMateriais());
        Set<Cor> cores = new HashSet<>(produto.getCores());
        produto.getMateriais().clear();
        produto.getCores().clear();
        Produto salvo = produtoRepository.saveAndFlush(produto);
        salvo.getMateriais().addAll(materiais);
        salvo.getCores().addAll(cores);
        return produtoRepository.saveAndFlush(salvo);
    }

    public long contarTodos() {
        return produtoRepository.count();
    }

    public Produto atualizar(Long id, Produto dados) {
        Produto produto = findById(id);
        if (!produto.getCodigo().equals(dados.getCodigo()) && produtoRepository.existsByCodigo(dados.getCodigo())) {
            throw new RuntimeException("Código já cadastrado: " + dados.getCodigo());
        }
        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setCodigo(dados.getCodigo());
        produto.setPreco(dados.getPreco());
        produto.setEstoque(dados.getEstoque());
        produto.setUnidadeMedida(dados.getUnidadeMedida());
        produto.setMaterial(dados.getMaterial());
        produto.setCategoria(dados.getCategoria());
        produto.setAtivo(dados.isAtivo());
        produto.setMaisVendido(dados.isMaisVendido());
        produto.setDestaque(dados.isDestaque());
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.delete(findById(id));
    }

    public Produto desativar(Long id) {
        Produto produto = findById(id);
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public Produto atualizarEstoque(Long id, Integer quantidade) {
        Produto produto = findById(id);
        int novoEstoque = produto.getEstoque() + quantidade;
        if (novoEstoque < 0) {
            throw new RuntimeException("Estoque insuficiente. Estoque atual: " + produto.getEstoque());
        }
        produto.setEstoque(novoEstoque);
        return produtoRepository.save(produto);
    }

    private Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
    }
}