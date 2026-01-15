package br.com.fiap.gestaoestabelecimento.dtos;


public record  AtualizaLoginSenhaUsuarioDTO (
    String email,
    String login,
    String senha,
    String tipoUsuario
){ }
