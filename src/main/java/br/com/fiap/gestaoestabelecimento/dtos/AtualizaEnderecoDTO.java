package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.UUID;

public record AtualizaEnderecoDTO (
        UUID idEndereco,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
){}
