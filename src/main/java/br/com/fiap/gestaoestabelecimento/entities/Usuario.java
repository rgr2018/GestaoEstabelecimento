package br.com.fiap.gestaoestabelecimento.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public abstract class Usuario  {
    protected Long id;
    protected String nome;
    protected String email;
    protected String login;
    protected String senha;
    protected TipoCliente tipoCliente;
    protected Date dataUltimaAlteracao;
    protected Endereco endereco;


}
