package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder  // ✅ era @Builder
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa {

    @NotNull(message = "Role é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name= "tipo_usuario",nullable = false, length = 20)
    private Role role;
}
