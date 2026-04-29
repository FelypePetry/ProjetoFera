package com.grupoum.projeto_fera.repository;

import com.grupoum.projeto_fera.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Produto> findByAtivoTrue();

    List<Produto> findByCategoriaIgnoreCase(String categoria);

    List<Produto> findByMaterialIgnoreCase(String material);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
