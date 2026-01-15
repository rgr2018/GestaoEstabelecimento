package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.UUID;

public record AtualizaClienteDTO(
    UUID idCliente,
    String dataAniversario,
    AtualizaUsuarioDTO usuarioDTO
){}
