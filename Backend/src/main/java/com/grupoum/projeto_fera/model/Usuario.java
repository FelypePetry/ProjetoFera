package com.grupoum.projeto_fera.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa {

    @NotNull(message = "Role é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name= "role",nullable = false, length = 20)
    private Role role;
}
