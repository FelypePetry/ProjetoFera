package com.grupoum.projeto_fera.repository;

import com.grupoum.projeto_fera.model.OrcamentoProduto;
import com.grupoum.projeto_fera.model.OrcamentoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoProdutoRepository extends JpaRepository<OrcamentoProduto, OrcamentoProdutoId> {
}
