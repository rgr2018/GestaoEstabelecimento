package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.UUID;

public record IncluiCardapioDTO(
     String nome,
     String descricao,
     Double preco,
     String indicadorDisponibilidade,
     String fotoPrato,
     UUID idEstabelecimento
){};
