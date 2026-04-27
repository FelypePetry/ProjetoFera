package com.grupoum.projeto_fera.service;


import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto salvar(Produto produto){
        if(produto.getPreco() <= 0){
            throw new RuntimeException("Produto deve ter preco maior que zero");
        }
        return repository.save(produto);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    public List<Produto> listarTodos(){
        return repository.findAll();
    }

    public Produto atualizar(Long id, Produto Dadosnovos){
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setNome(Dadosnovos.getNome());
        existente.setPreco(Dadosnovos.getPreco());
        existente.setDescricao(Dadosnovos.getDescricao());
        existente.setQuantidade(Dadosnovos.getQuantidade());

        return repository.save(existente);
    }


}
