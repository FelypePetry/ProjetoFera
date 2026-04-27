package com.grupoum.projeto_fera.controller;

import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public List<Produto> listarTodos(){
        return service.listarTodos();
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto){
        return service.salvar(produto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id){
        service.deletar(id);
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto){
        return service.atualizar(id, produto);
    }

}
