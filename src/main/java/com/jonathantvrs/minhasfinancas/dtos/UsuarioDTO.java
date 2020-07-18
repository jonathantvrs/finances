package com.jonathantvrs.minhasfinancas.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UsuarioDTO {
    private String email;
    private String nome;
    private String senha;
}
