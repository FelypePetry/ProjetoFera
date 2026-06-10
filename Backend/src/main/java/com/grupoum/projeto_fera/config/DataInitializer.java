package com.grupoum.projeto_fera.config;

import com.grupoum.projeto_fera.model.*;
import com.grupoum.projeto_fera.repository.*;
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
    private final MaterialRepository materialRepository;
    private final CategoriaRepository categoriaRepository;
    private final CorRepository corRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(Usuario.builder()
                    .nome("Administrador")
                    .email("admin@metalurgica.com")
                    .senha(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .ativo(true)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nome("Operador")
                    .email("operador@metalurgica.com")
                    .senha(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_USER)
                    .ativo(true)
                    .build());

            usuarioRepository.save(Usuario.builder()
                    .nome("Théo")
                    .email("theo@gmail.com")
                    .senha(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_CLIENTE)
                    .ativo(true)
                    .build());

            log.info("Usuários padrão criados: admin@metalurgica.com / admin123 | operador@metalurgica.com / user123");
        }

        if (materialRepository.count() == 0) {
            materialRepository.save(Material.builder().nome("Aço Inox 304").build());
            materialRepository.save(Material.builder().nome("Alumínio").build());
            materialRepository.save(Material.builder().nome("Ferro").build());
        }

        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(Categoria.builder().nome("Chapas").build());
            categoriaRepository.save(Categoria.builder().nome("Tubos").build());
            categoriaRepository.save(Categoria.builder().nome("Barras").build());
        }

        if (corRepository.count() == 0) {
            corRepository.save(Cor.builder().nome("Cinza").build());
            corRepository.save(Cor.builder().nome("Preto").build());
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

            log.info("Produtos de exemplo criados!");
        }
    }
}
