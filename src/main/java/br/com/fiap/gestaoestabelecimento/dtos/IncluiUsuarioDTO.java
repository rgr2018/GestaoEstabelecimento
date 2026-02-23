package br.com.fiap.gestaoestabelecimento.dtos;

public record IncluiUsuarioDTO(
        String nome,
        String email,
        String login,
        String senha,
        String tipoUsuario,
        IncluiEnderecoDTO endereco
) {
}
