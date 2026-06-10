package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Convert(converter = NotaConverter.class)
    @Column(name = "nota", nullable = false, columnDefinition = "nota_enum")
    private Nota nota;

    @Column(name = "comentario", length = 500)
    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDate dataAvaliacao;

    @Builder.Default
    @Column(name = "moderado")
    private Boolean moderado = false;

    public enum Nota {
        UM, DOIS, TRES, QUATRO, CINCO
    }

    @PrePersist
    protected void onCreate() {
        dataAvaliacao = LocalDate.now();
        if (moderado == null) moderado = false;
    }
}
