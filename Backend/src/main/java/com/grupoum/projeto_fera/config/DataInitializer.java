package com.grupoum.projeto_fera.config;

import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.model.Role;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = Usuario.builder()
                    .nome("Administrador")
                    .email("admin@metalurgica.com")
                    .senha(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .ativo(true)
                    .build();

            Usuario user = Usuario.builder()
                    .nome("Operador")
                    .email("operador@metalurgica.com")
                    .senha(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_USER)
                    .ativo(true)
                    .build();

            usuarioRepository.save(admin);
            usuarioRepository.save(user);
            log.info("✅ Usuários padrão criados: admin@metalurgica.com / admin123 | operador@metalurgica.com / user123");
        }

        if (produtoRepository.count() == 0) {
            produtoRepository.save(Produto.builder()
                    .nome("Chapa de Aço Inox 304")
                    .descricao("Chapa de aço inoxidável 304, acabamento 2B")
                    .codigo("CHAPA-INOX-304")
                    .preco(new BigDecimal("850.00"))
                    .estoque(120)
                    .unidadeMedida("m²")
                    .material("Aço Inox 304")
                    .categoria("Chapas")
                    .ativo(true)
                    .build());

            produtoRepository.save(Produto.builder()
                    .nome("Tubo Redondo Alumínio")
                    .descricao("Tubo redondo de alumínio 1 polegada, 3m de comprimento")
                    .codigo("TUBO-AL-1POL")
                    .preco(new BigDecimal("45.90"))
                    .estoque(500)
                    .unidadeMedida("un")
                    .material("Alumínio")
                    .categoria("Tubos")
                    .ativo(true)
                    .build());

            produtoRepository.save(Produto.builder()
                    .nome("Barra Chata Ferro 1\"x3/16\"")
                    .descricao("Barra chata de ferro laminada a quente")
                    .codigo("BARRA-FE-1X316")
                    .preco(new BigDecimal("18.50"))
                    .estoque(300)
                    .unidadeMedida("m")
                    .material("Ferro")
                    .categoria("Barras")
                    .ativo(true)
                    .build());

            log.info("✅ Produtos de exemplo criados!");
        }
    }
}
