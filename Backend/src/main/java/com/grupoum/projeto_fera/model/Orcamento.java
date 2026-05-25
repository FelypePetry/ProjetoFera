package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrcamentoProduto> orcamentoProdutos;

    @NotBlank(message = "Tipo de móvel é obrigatório")
    @Column(name = "tipo_movel", nullable = false, length = 100)
    private String tipoMovel;

    @Column(name= "medidas",length = 200)
    private String medidas;

    @Column(name= "observacoes",length = 1000)
    private String observacoes;

    @Column(name = "imagem", length = 300)
    private String imagemPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrcamento status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_solicitacao", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    private Integer progresso = 0;

    public Integer getProgresso() {
        return switch (this.status) {
            case PENDENTE -> 10;
            case EM_ANALISE -> 30;
            case APROVADO -> 50;
            case EM_PRODUCAO -> 70;
            case FINALIZADO -> 100;
            case CANCELADO -> 0;
        };
    }

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
        status = StatusOrcamento.PENDENTE;
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
