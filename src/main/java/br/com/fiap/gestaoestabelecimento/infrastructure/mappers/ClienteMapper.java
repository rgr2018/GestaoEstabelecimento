package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;
import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaClienteDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
public final class ClienteMapper {

    private ClienteMapper() {}

    public static ClienteEntity toEntity(IncluiClienteDTO dto) {
        ClienteEntity entity = new ClienteEntity();
        entity.setDataAniversario(dto.dataAniversario());
        return entity;
    }

    public static ClienteEntity toEntityAtualiza(ClienteEntity clienteEntity, UsuarioEntity usuarioEntity, AtualizaClienteDTO dto) {

        clienteEntity.setDataAniversario(dto.dataAniversario());
        clienteEntity.setUsuarioEntity(usuarioEntity);
        return clienteEntity;
    }

    public static Cliente toDomain(ClienteEntity entity) {
        UsuarioEntity usuario = entity.getUsuarioEntity();
        return new Cliente(
                entity.getIdCliente(),
                entity.getDataAniversario(),
                usuario != null
                        ? UsuarioMapper.toDomain(usuario)
                        : null
        );
    }

    public static Cliente toDomainAtualiza(ClienteEntity entity, UsuarioEntity usuario) {
        return new Cliente(
                entity.getIdCliente(),
                entity.getDataAniversario(),
                usuario != null
                        ? UsuarioMapper.toDomain(usuario)
                        : null
        );
    }
}