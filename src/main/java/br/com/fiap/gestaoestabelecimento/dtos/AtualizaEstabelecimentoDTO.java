package br.com.fiap.gestaoestabelecimento.dtos;
import java.util.UUID;

public record AtualizaEstabelecimentoDTO(
        UUID idEstabelecimento,
        String tipoCozinha,
        String tipoEstabelecimento,
        String cnpj,
        String nome,
        String horarioAberturaDiaSemana,
        String horarioFechamentoDiaSemana,
        String horarioAberturaFeriadoFimSemana,
        String horarioFechamentoFeriadoFimSemana,
        AtualizaEnderecoDTO endereco
) {
}