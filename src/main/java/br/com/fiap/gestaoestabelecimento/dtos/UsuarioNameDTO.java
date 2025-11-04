package br.com.fiap.gestaoestabelecimento.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioNameDTO {
    public Long id;
    public String nome;
    public String email;
    public String login;
}