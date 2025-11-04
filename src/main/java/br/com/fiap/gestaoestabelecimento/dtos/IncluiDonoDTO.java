package br.com.fiap.gestaoestabelecimento.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluiDonoDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String tipoCliente;
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String nomeEstabelecimento;
    private String tipoEstabelecimento;
}
