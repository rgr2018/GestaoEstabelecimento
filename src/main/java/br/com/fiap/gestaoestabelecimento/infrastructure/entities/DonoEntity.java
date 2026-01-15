package br.com.fiap.gestaoestabelecimento.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name= "Dono")
public class DonoEntity implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idDono;

    @Column()
    private LocalDateTime dataHoraCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "idUsuario",nullable = false)
    private UsuarioEntity usuarioEntity;

    @ManyToMany
    @JoinTable(
            name = "dono_estabelecimento",
            joinColumns = @JoinColumn(name ="idDono"),
            inverseJoinColumns = @JoinColumn(name="idEstabelecimento")
    )
    private Set<EstabelecimentoEntity> estabelecimentoEntities= new HashSet<>();

    public UUID getIdDono() {
        return idDono;
    }

    public void setIdDono(UUID idDono) {
        this.idDono = idDono;
    }

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

    public Set<EstabelecimentoEntity> getEstabelecimentoEntities() {
        return estabelecimentoEntities;
    }

    public void setEstabelecimentoEntities(Set<EstabelecimentoEntity> estabelecimentoEntities) {
        this.estabelecimentoEntities = estabelecimentoEntities;
    }
}
