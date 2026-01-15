package br.com.fiap.gestaoestabelecimento.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record IncluiClienteDTO(
        String dataAniversario,
        IncluiUsuarioDTO usuarioDTO
    )
{}
