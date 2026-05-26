package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.ImagemProd;
import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

        Produto salvo = produtoRepository.save(produto);

        List<ImagemProd> imagens = new ArrayList<>();
        for (MultipartFile arquivo : arquivos) {
            if (!arquivo.isEmpty()) {
                String url = imageUploadService.save(arquivo);
                ImagemProd img = new ImagemProd();
                img.setUrlImagem(url);
                img.setProduto(salvo);
                imagens.add(img);
            }
        }

        salvo.setImagens(imagens);
        return produtoRepository.save(salvo);
    }

    public long contarTodos(){
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
        produto.setMateriais(dados.getMateriais());
        produto.setCores(dados.getCores());
        produto.setCategoria(dados.getCategoria());
        produto.setAtivo(dados.isAtivo());

        // Limpa a lista de imagens antigas e adiciona as novas
        produto.getImagens().clear();
        if (dados.getImagens() != null) {
            dados.getImagens().forEach(img -> {
                img.setProduto(produto); // Garante a associação bidirecional
                produto.getImagens().add(img);
            });
        }
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
