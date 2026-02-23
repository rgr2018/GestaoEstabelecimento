package br.com.fiap.gestaoestabelecimento.dtos;

import jakarta.persistence.Column;

public record IncluiEnderecoDTO (
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
){
}
