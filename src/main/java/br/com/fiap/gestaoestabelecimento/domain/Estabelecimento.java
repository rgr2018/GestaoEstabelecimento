package br.com.fiap.gestaoestabelecimento.domain;
import java.util.List;
import java.util.UUID;

public class Estabelecimento {

    private UUID idEstabelecimento;
    private final String nome;
    private final String cnpj;
    private final String tipoCozinha;
    private final String tipoEstabelecimento;
    private final String horarioAberturaDiaSemana;
    private final String horarioFechamentoDiaSemana;
    private final String horarioAberturaFeriadoFimSemana;
    private final String horarioFechamentoFeriadoFimSemana;
    private final Endereco endereco;
    private List<Dono> donos;
    private List<Cardapio> cardapios;

    public Estabelecimento(
            UUID idEstabelecimento,
            String nome,
            String cnpj,
            String tipoCozinha,
            String tipoEstabelecimento,
            String horarioAberturaDiaSemana,
            String horarioFechamentoDiaSemana,
            String horarioAberturaFeriadoFimSemana,
            String horarioFechamentoFeriadoFimSemana,
            Endereco endereco,
            List<Dono> donos,
            List<Cardapio> cardapios
    ) {
        this.idEstabelecimento = idEstabelecimento;
        this.nome = nome;
        this.cnpj = cnpj;
        this.tipoCozinha = tipoCozinha;
        this.tipoEstabelecimento = tipoEstabelecimento;
        this.horarioAberturaDiaSemana = horarioAberturaDiaSemana;
        this.horarioFechamentoDiaSemana = horarioFechamentoDiaSemana;
        this.horarioAberturaFeriadoFimSemana = horarioAberturaFeriadoFimSemana;
        this.horarioFechamentoFeriadoFimSemana = horarioFechamentoFeriadoFimSemana;
        this.endereco = endereco;
        this.donos = donos;
        this.cardapios = cardapios;

    }

    // getters apenas (imutável)
    public void setIdEstabelecimento(UUID idEstabelecimento) {
        this.idEstabelecimento = idEstabelecimento;
    }

    public UUID getIdEstabelecimento() { return idEstabelecimento; }
    public String getNome() { return nome; }
    public String getCnpj() { return cnpj; }
    public Endereco getEndereco() {return endereco; }
    public List<Dono> getDonos() {return donos;}
    public List<Cardapio> getCardapios() { return cardapios; }


}