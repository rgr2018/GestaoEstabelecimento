package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;
import br.com.fiap.gestaoestabelecimento.domain.Endereco;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEnderecoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEnderecoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;

public final class EnderecoMapper {

    private EnderecoMapper() {}

    public static EnderecoEntity toEntity(IncluiEnderecoDTO dto) {

        EnderecoEntity entity = new EnderecoEntity();
        entity.setLogradouro(dto.logradouro());
        entity.setNumero(dto.numero());
        entity.setBairro(dto.bairro());
        entity.setCidade(dto.cidade());
        entity.setEstado(dto.estado());
        entity.setCep(dto.cep());
        entity.setComplemento(dto.complemento());
        return entity;
    }

    public static EnderecoEntity toEntityAtualiza(EnderecoEntity enderecoEntity, AtualizaEnderecoDTO dto) {

        enderecoEntity.setLogradouro(dto.logradouro());
        enderecoEntity.setNumero(dto.numero());
        enderecoEntity.setBairro(dto.bairro());
        enderecoEntity.setCidade(dto.cidade());
        enderecoEntity.setEstado(dto.estado());
        enderecoEntity.setCep(dto.cep());
        enderecoEntity.setComplemento(dto.complemento());
        return enderecoEntity;
    }

    public static Endereco toDomain(EnderecoEntity entity) {

        Endereco endereco = new Endereco(
                entity.getIdEndereco(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep(),
                entity.getComplemento()
        );
        endereco.setIdEndereco(entity.getIdEndereco());
        return endereco;
    }
}