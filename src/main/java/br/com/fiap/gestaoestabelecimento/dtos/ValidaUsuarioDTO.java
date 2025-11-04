package br.com.fiap.gestaoestabelecimento.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidaUsuarioDTO {
    private String login;
    private String senha;
    private String email;
}
