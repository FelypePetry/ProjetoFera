package com.grupoum.projeto_fera.repository;

import com.grupoum.projeto_fera.model.Orcamento;
import com.grupoum.projeto_fera.model.StatusOrcamento;
import com.grupoum.projeto_fera.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByUsuario(Usuario usuario);
    List<Orcamento> findByStatus(StatusOrcamento status);
    List<Orcamento> findAllByOrderByCriadoEmDesc();
    long countByStatus(StatusOrcamento status);
}
