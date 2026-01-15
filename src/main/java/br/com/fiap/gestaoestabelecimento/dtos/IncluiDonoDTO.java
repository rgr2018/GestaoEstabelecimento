package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.UUID;

public record IncluiDonoDTO (
        IncluiUsuarioDTO usuarioDTO,
        UUID idEstabelecimento
)
{}
