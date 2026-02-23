package br.com.fiap.gestaoestabelecimento.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name= "Endereco")
public class EnderecoEntity {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEndereco;

    @Column(nullable = false, length = 200)
    private String logradouro;

    @Column(nullable = true, length = 20)
    private String numero;

    @Column(nullable = true, length = 20)
    private String bairro;

    @Column(nullable = true, length = 30)
    private String cidade;

    @Column(nullable = true, length = 20)
    private String estado;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = true, length = 200)
    private String complemento;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "enderecoEntity",fetch = FetchType.LAZY)
    private Set<EstabelecimentoEntity> estabelecimentoEntities = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "enderecoEntity",fetch = FetchType.LAZY)
    private Set<UsuarioEntity> usuarioEntities = new HashSet<>();

    public UUID getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(UUID idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<EstabelecimentoEntity> getEstabelecimentoEntities() {
        return estabelecimentoEntities;
    }

    public void setEstabelecimentoEntities(Set<EstabelecimentoEntity> estabelecimentoEntities) {
        this.estabelecimentoEntities = estabelecimentoEntities;
    }

    public Set<UsuarioEntity> getUsuarioEntities() {
        return usuarioEntities;
    }

    public void setUsuarioEntities(Set<UsuarioEntity> usuarioEntities) {
        this.usuarioEntities = usuarioEntities;
    }



}
