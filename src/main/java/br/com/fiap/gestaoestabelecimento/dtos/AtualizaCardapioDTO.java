package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.Set;
import java.util.UUID;

public record AtualizaCardapioDTO(
        UUID idCardapio,
        String nome,
        String descricao,
        Double preco,
        String indicadorDisponibilidade,
        String fotoPrato,
        UUID idEstabelecimento
){};