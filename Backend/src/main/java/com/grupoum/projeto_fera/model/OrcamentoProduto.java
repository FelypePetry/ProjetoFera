package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orcamento_produto")
@IdClass(OrcamentoProdutoId.class)
public class OrcamentoProduto {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Id
    @ManyToOne
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Id
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;
}
