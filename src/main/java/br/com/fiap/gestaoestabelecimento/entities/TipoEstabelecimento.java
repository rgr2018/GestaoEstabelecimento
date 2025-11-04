package br.com.fiap.gestaoestabelecimento.entities;


public enum TipoEstabelecimento {
    RESTAURANTE("001", "Restaurante"),
    PIZZARIA("002", "Pizzaria");

    private final String codigo;
    private final String descricao;

    TipoEstabelecimento(String codigo, String descricao) {
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
