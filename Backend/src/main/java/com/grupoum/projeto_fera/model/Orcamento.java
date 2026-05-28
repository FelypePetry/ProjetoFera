package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrcamentoProduto> orcamentoProdutos = new java.util.HashSet<>();

    @NotBlank(message = "Tipo de móvel é obrigatório")
    @Column(name = "tipo_movel", nullable = false, length = 100)
    private String tipoMovel;

    @Column(name= "medidas",length = 200)
    private String medidas;

    @Column(name= "observacoes",length = 1000)
    private String observacoes;

    @Column(name = "imagem_path", length = 300)
    private String imagemPath;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrcamento status = StatusOrcamento.PENDENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    @Builder.Default
    private Integer progresso = 0;

    public Integer getProgresso() {
        if (this.status == null) {
            return 0;
        }
        return switch (this.status) {
            case PENDENTE -> 10;
            case EM_ANALISE -> 30;
            case APROVADO -> 50;
            case EM_PRODUCAO -> 70;
            case FINALIZADO -> 100;
            case CANCELADO -> 0;
        };
    }

    @Transient
    private List<Long> produtosSelecionadosIds;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
