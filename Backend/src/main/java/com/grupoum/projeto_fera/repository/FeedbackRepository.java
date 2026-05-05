package com.grupoum.projeto_fera.repository;

import com.grupoum.projeto_fera.model.Feedback;
import com.grupoum.projeto_fera.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUsuario(Usuario usuario);
    List<Feedback> findAllByOrderByCriadoEmDesc();
}
