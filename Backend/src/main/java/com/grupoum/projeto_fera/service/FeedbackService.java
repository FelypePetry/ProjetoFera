package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Feedback;
import com.grupoum.projeto_fera.model.Orcamento;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.FeedbackRepository;
import com.grupoum.projeto_fera.repository.OrcamentoRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrcamentoRepository orcamentoRepository;

    @Transactional(readOnly = true)
    public List<Feedback> listarTodos() {
        return feedbackRepository.findAllByOrderByCriadoEmDesc();
    }

    @Transactional(readOnly = true)
    public List<Feedback> listarPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return feedbackRepository.findByUsuario(usuario);
    }

    public Feedback criar(Long idOrcamento, Feedback.Nota nota, String comentario, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Orcamento orcamento = orcamentoRepository.findById(idOrcamento)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));

        Feedback feedback = Feedback.builder()
                .orcamento(orcamento)
                .usuario(usuario)
                .nota(nota)
                .comentario(comentario)
                .build();

        return feedbackRepository.save(feedback);
    }

    public void deletar(Long id) {
        feedbackRepository.deleteById(id);
    }
}
