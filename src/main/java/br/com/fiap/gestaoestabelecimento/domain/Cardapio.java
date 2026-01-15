package br.com.fiap.gestaoestabelecimento.domain;

import java.util.UUID;

public class Cardapio {

        private UUID id;
        private String nome;
        private String descricao;
        private Double preco;
        private String indicadorDisponibilidade;
        private String fotoPrato;


        public Cardapio(
                String nome,
                String descricao,
                Double preco,
                String indicadorDisponibilidade,
                String fotoPrato
        ) {
            this.nome = nome;
            this.descricao = descricao;
            this.preco = preco;
            this.indicadorDisponibilidade = indicadorDisponibilidade;
            this.fotoPrato = fotoPrato;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public Double getPreco() { return preco; }
        public String getIndicadorDisponibilidade() { return indicadorDisponibilidade; }
        public String getFotoPrato() { return fotoPrato; }
}