package br.com.fiap.gestaoestabelecimento.infrastructure.entities;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiUsuarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "Cliente")
public class ClienteEntity implements Serializable {


    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCliente;


    @Column
    private String dataAniversario;

    @Column()
    private LocalDateTime dataHoraCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "idUsuario",nullable = false)
    private UsuarioEntity usuarioEntity;

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getDataAniversario() { return dataAniversario; }

    public void setDataAniversario(String dataAniversario) { this.dataAniversario = dataAniversario; }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }


}

