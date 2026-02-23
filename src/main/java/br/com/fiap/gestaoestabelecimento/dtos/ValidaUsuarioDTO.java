package br.com.fiap.gestaoestabelecimento.dtos;

public record ValidaUsuarioDTO (
    String login,
    String senha,
    String email
){
}
