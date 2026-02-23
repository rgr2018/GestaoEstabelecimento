package br.com.fiap.gestaoestabelecimento.infrastructure.entities;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name= "Cardapio")
public class CardapioEntity implements Serializable {
    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCardapio;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private String indicadorDisponibilidade;

    @Column(nullable = false)
    private String fotoPrato;

    @Column()
    private LocalDateTime dataHoraCadastro;

    @ManyToMany
    @JoinTable(
            name = "cardapio_estabelecimento",
            joinColumns = @JoinColumn(name = "idCardapio"),
            inverseJoinColumns = @JoinColumn(name="idEstabelecimento")
    )
    private Set<EstabelecimentoEntity> estabelecimentoEntities = new HashSet<>();

    public UUID getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(UUID idCardapio) {
        this.idCardapio = idCardapio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getIndicadorDisponibilidade() {
        return indicadorDisponibilidade;
    }

    public void setIndicadorDisponibilidade(String indicadorDisponibilidade) {
        this.indicadorDisponibilidade = indicadorDisponibilidade;
    }

    public String getFotoPrato() {
        return fotoPrato;
    }

    public void setFotoPrato(String fotoPrato) {
        this.fotoPrato = fotoPrato;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Set<EstabelecimentoEntity> getEstabelecimentoEntities() {
        return estabelecimentoEntities;
    }

    public void setEstabelecimentoEntities(Set<EstabelecimentoEntity> estabelecimentoEntities) {
        this.estabelecimentoEntities = estabelecimentoEntities;
    }



}

