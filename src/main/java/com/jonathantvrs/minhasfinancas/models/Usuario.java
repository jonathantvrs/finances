package com.jonathantvrs.minhasfinancas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa um usuário no sistema
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario", schema = "financas")
public class Usuario {

    /**
     * Identifica unicamente um usuário no sistema
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário
     */
    @Column(name = "nome")
    private String nome;

    /**
     * Email do usuário
     */
    @Column(name = "email")
    private String email;

    /**
     * Senha do usuário
     */
    @Column(name = "senha")
    private String senha;
}
