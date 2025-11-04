package br.com.fiap.gestaoestabelecimento.entities;

import lombok.Getter;

@Getter
public enum TipoCliente {
    CLIENTE("001", "Cliente"),
    DONO("002", "Dono Restaurante");

    private final String codigo;
    private final String descricao;

    TipoCliente(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

}
