package com.grupoum.projeto_fera.service;

import com.grupoum.projeto_fera.model.Cliente;
import com.grupoum.projeto_fera.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o email: " + email));
    }

    // Outros métodos específicos para Cliente podem ser adicionados aqui.
}