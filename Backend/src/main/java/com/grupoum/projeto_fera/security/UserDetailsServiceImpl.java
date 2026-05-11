package com.grupoum.projeto_fera.security;

import com.grupoum.projeto_fera.model.Cliente;
import com.grupoum.projeto_fera.model.Role;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.ClienteRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return new User(
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.isAtivo(),
                    true,
                    true,
                    true,
                    List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
            );
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return new User(
                    cliente.getEmail(),
                    cliente.getSenha(),
                    cliente.isAtivo(),
                    true,
                    true,
                    true,
                    List.of(new SimpleGrantedAuthority(Role.ROLE_CLIENTE.name()))
            );
        }

        throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }
}
