package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orcamento", nullable = false)
    private Orcamento orcamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "nota", nullable = false)
    private Nota nota;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDate dataAvaliacao;

    @Column(name = "moderado")
    private Boolean moderado;

    // Enum para o campo nota
    public enum Nota {
        UM, DOIS, TRES, QUATRO, CINCO
    }

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        dataAvaliacao = LocalDate.now();
    }
}
