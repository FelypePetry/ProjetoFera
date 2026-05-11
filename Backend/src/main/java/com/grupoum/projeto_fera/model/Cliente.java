package com.grupoum.projeto_fera.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente extends Pessoa {

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cidade")
    private String cidade;
}
