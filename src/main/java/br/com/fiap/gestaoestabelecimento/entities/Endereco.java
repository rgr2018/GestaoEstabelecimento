package br.com.fiap.gestaoestabelecimento.entities;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Endereco {
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
}
