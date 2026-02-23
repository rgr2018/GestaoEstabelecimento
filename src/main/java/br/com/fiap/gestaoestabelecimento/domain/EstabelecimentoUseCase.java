package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.EstabelecimentoGatewayImp;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EstabelecimentoUseCase {

    private final EstabelecimentoGatewayImp estabelecimentoGatewayImp;

    public EstabelecimentoUseCase(EstabelecimentoGatewayImp estabelecimentoGatewayImp) {
        this.estabelecimentoGatewayImp = estabelecimentoGatewayImp;
    }

    // -------------------------------------------------------------------------
    //  USE CASE — INCLUIR
    // -------------------------------------------------------------------------
    public Estabelecimento incluirEstabelecimentoUseCase(IncluiEstabelecimentoDTO dto) {
        return estabelecimentoGatewayImp.saveEstabelecimento(dto);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — ATUALIZAR
    // -------------------------------------------------------------------------
    public void updateEstabelecimentoUseCase(AtualizaEstabelecimentoDTO dto) {
        estabelecimentoGatewayImp.updateEstabelecimento(dto);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — BUSCAR POR ID
    // -------------------------------------------------------------------------
    public Estabelecimento buscarEstabelecimentoIdUseCase(UUID id) {
        return estabelecimentoGatewayImp.getEstabelecimento(id);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — BUSCAR POR NOME
    // -------------------------------------------------------------------------
    public List<Estabelecimento> buscarEstabelecimentoNomeUseCase(String nome) {
        return estabelecimentoGatewayImp.buscaPorNome(nome);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — DELETAR
    // -------------------------------------------------------------------------
    public void deleteEstabelecimentoUseCase(UUID idEstabelecimento) {
        estabelecimentoGatewayImp.deleteEstabelecimento(idEstabelecimento);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — LISTAR
    // -------------------------------------------------------------------------
    public Iterable<Estabelecimento> listarEstabelecimentoUseCase() {
        return estabelecimentoGatewayImp.getAllEstabelecimento();
    }
}
