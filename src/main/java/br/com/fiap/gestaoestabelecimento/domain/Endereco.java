package br.com.fiap.gestaoestabelecimento.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Endereco {
    private UUID idEndereco;
    private final String logradouro;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;
    private final String complemento;

    public Endereco(
            UUID idEndereco,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String estado,
            String cep,
            String complemento
    ) {
        this.idEndereco = idEndereco;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    public UUID getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(UUID idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }
}