package br.com.fiap.gestaoestabelecimento.domain;

import lombok.Getter;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Usuario {

    protected UUID idUsuario;
    protected String nome;
    protected String email;
    protected String login;
    protected String senha;
    protected String tipoUsuario;
    protected String dataUltimaAlteracao;
    protected Endereco endereco;

    public Usuario(
            UUID idUsuario,
            String nome,
            String email,
            String login,
            String senha,
            String tipoUsuario,
            String dataUltimaAlteracao,
            Endereco endereco
    ) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.endereco = endereco;
    }


    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}