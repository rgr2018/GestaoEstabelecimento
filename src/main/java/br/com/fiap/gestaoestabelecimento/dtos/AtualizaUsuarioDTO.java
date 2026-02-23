package br.com.fiap.gestaoestabelecimento.dtos;


import java.util.UUID;

public record AtualizaUsuarioDTO (
        UUID idUsuario,
        String nome,
        String email,
        String login,
        String senha,
        String tipoUsuario,
        AtualizaEnderecoDTO endereco
){
}
