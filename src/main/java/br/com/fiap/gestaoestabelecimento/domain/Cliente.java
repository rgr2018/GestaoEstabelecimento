package br.com.fiap.gestaoestabelecimento.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario {

    private final UUID idCliente;
    private final String dataAniversario;

    public Cliente(
            UUID idCliente,
            String dataAniversario,
            Usuario usuario
    ) {
        super(usuario.getIdUsuario(),
              usuario.getNome(),
              usuario.getEmail(),
              usuario.getLogin(),
              usuario.getSenha(),
              usuario.getTipoUsuario(),
              usuario.getDataUltimaAlteracao(),
              usuario.getEndereco());
        this.dataAniversario = dataAniversario;
        this.idCliente =   idCliente;


    }

}
