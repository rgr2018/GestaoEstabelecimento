package br.com.fiap.gestaoestabelecimento.dtos;

import java.util.UUID;

public record IncluiEstabelecimentoDTO (
        String tipoCozinha,
        String tipoEstabelecimento,
        String nome,
        String cnpj,
        String horarioAberturaDiaSemana,
        String horarioFechamentoDiaSemana,
        String horarioAberturaFeriadoFimSemana,
        String horarioFechamentoFeriadoFimSemana,
        IncluiEnderecoDTO endereco
)
{}
