package com.grupoum.projeto_fera.model;

import com.grupoum.projeto_fera.model.Produto;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imagem_prod")
public class ImagemProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem")
    private Long id;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Column(name = "url_imagem", nullable = false, length = 500)
    private String urlImagem;

    @Column(name = "descricao_alt", length = 200)
    private String descricaoAlt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    @ToString.Exclude
    private Produto produto;
}