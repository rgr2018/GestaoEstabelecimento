package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.Set;
import java.util.UUID;

public record AtualizaDonoDTO (
        UUID idDono,
        AtualizaUsuarioDTO usuarioDTO,
        UUID idEstabelecimento
        )
{
}
