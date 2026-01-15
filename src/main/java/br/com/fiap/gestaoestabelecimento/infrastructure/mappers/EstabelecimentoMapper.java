package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public class EstabelecimentoMapper {

    private EstabelecimentoMapper() {}

    public static EstabelecimentoEntity toEntity(IncluiEstabelecimentoDTO dto) {
        EstabelecimentoEntity entity = new EstabelecimentoEntity();

        entity.setDataHoraCadastro(LocalDateTime.now());
        entity.setNome(dto.nome());
        entity.setCNPJ(dto.cnpj());
        entity.setTipoCozinha(dto.tipoCozinha());
        entity.setTipoEstabelecimento(dto.tipoEstabelecimento());
        entity.setHorarioAberturaDiaSemana(dto.horarioAberturaDiaSemana());
        entity.setHorarioFechamentoDiaSemana(dto.horarioFechamentoDiaSemana());
        entity.setHorarioAberturaFeriadoFimSemana(dto.horarioAberturaFeriadoFimSemana());
        entity.setHorarioFechamentoFeriadoFimSemana(dto.horarioFechamentoFeriadoFimSemana());
        return entity;
    }


    public static EstabelecimentoEntity toEntityAtualiza(EstabelecimentoEntity entity, AtualizaEstabelecimentoDTO dto) {

        entity.setIdEstabelecimento(dto.idEstabelecimento());
        entity.setDataHoraCadastro(LocalDateTime.now());
        entity.setNome(dto.nome());
        entity.setCNPJ(dto.cnpj());
        entity.setTipoCozinha(dto.tipoCozinha());
        entity.setTipoEstabelecimento(dto.tipoEstabelecimento());
        entity.setHorarioAberturaDiaSemana(dto.horarioAberturaDiaSemana());
        entity.setHorarioFechamentoDiaSemana(dto.horarioFechamentoDiaSemana());
        entity.setHorarioAberturaFeriadoFimSemana(dto.horarioAberturaFeriadoFimSemana());
        entity.setHorarioFechamentoFeriadoFimSemana(dto.horarioFechamentoFeriadoFimSemana());

        return entity;
    }

    public static Estabelecimento toDomain(EstabelecimentoEntity entity) {

        if (entity == null) {
            return null;
        }
        EnderecoEntity endereco = entity.getEnderecoEntity();

        Estabelecimento estabelecimento = new Estabelecimento(
                entity.getIdEstabelecimento(),
                entity.getNome(),
                entity.getCNPJ(),
                entity.getTipoCozinha(),
                entity.getTipoEstabelecimento(),
                entity.getHorarioAberturaDiaSemana(),
                entity.getHorarioFechamentoDiaSemana(),
                entity.getHorarioAberturaFeriadoFimSemana(),
                entity.getHorarioFechamentoFeriadoFimSemana(),
                endereco != null ? EnderecoMapper.toDomain(endereco) : null,
                entity.getDonoEntities() != null
                        ? entity.getDonoEntities()
                        .stream()
                        .map(DonoMapper::toDomain)
                        .toList()
                        : List.of(),
                entity.getCardapioEntities() != null
                        ? entity.getCardapioEntities()
                        .stream()
                        .map(CardapioMapper::toDomain)
                        .toList()
                        : List.of()
        );

        return estabelecimento;
    }
}
