package br.com.fiap.gestaoestabelecimento.dtos;

public record AtualizaLoginSenhaDTO (
        String email,
        String login,
        String senha
){ }