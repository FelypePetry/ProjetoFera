package com.grupoum.projeto_fera.config;

import com.grupoum.projeto_fera.model.Categoria;
import com.grupoum.projeto_fera.model.Material;
import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.model.Role;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.repository.CategoriaRepository;
import com.grupoum.projeto_fera.repository.MaterialRepository;
import com.grupoum.projeto_fera.repository.ProdutoRepository;
import com.grupoum.projeto_fera.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final CategoriaRepository categoriaRepository;
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

            Usuario cliente = Usuario.builder()
                    .nome("Théo")
                    .email("theo@gmail.com")
                    .senha(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_CLIENTE)
                    .ativo(true)
                    .build();

            usuarioRepository.save(admin);
            usuarioRepository.save(user);
            usuarioRepository.save(cliente);
            log.info("✅ Usuários padrão criados: admin@metalurgica.com / admin123 | operador@metalurgica.com / user123");
        }

        if (produtoRepository.count() == 0) {
            Material acoInox = materialRepository.save(Material.builder().nome("Aço Inox 304").build());
            Material aluminio = materialRepository.save(Material.builder().nome("Alumínio").build());
            Material ferro = materialRepository.save(Material.builder().nome("Ferro").build());

            Categoria chapas = categoriaRepository.save(Categoria.builder().nome("Chapas").build());
            Categoria tubos = categoriaRepository.save(Categoria.builder().nome("Tubos").build());
            Categoria barras = categoriaRepository.save(Categoria.builder().nome("Barras").build());

            produtoRepository.save(Produto.builder()
                    .nome("Chapa de Aço Inox 304")
                    .descricao("Chapa de aço inoxidável 304, acabamento 2B")
                    .codigo("CHAPA-INOX-304")
                    .preco(new BigDecimal("850.00"))
                    .estoque(120)
                    .materiais(Set.of(acoInox))
                    .categoria(chapas)
                    .ativo(true)
                    .build());

            produtoRepository.save(Produto.builder()
                    .nome("Tubo Redondo Alumínio")
                    .descricao("Tubo redondo de alumínio 1 polegada, 3m de comprimento")
                    .codigo("TUBO-AL-1POL")
                    .preco(new BigDecimal("45.90"))
                    .estoque(500)
                    .materiais(Set.of(aluminio))
                    .categoria(tubos)
                    .ativo(true)
                    .build());

            produtoRepository.save(Produto.builder()
                    .nome("Barra Chata Ferro 1\"x3/16\"")
                    .descricao("Barra chata de ferro laminada a quente")
                    .codigo("BARRA-FE-1X316")
                    .preco(new BigDecimal("18.50"))
                    .estoque(300)
                    .materiais(Set.of(ferro))
                    .categoria(barras)
                    .ativo(true)
                    .build());

            log.info("✅ Produtos de exemplo criados!");
        }
    }
}
