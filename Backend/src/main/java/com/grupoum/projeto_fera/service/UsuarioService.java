package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
    }

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = findById(id);
        if (!usuario.getEmail().equals(dados.getEmail()) && usuarioRepository.existsByEmail(dados.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + dados.getEmail());
        }
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setRole(dados.getRole());
        usuario.setAtivo(dados.isAtivo());
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.delete(findById(id));
    }

    public Usuario desativar(Long id) {
        Usuario usuario = findById(id);
        usuario.setAtivo(false);
        return usuarioRepository.save(usuario);
    }

    private Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }
}
