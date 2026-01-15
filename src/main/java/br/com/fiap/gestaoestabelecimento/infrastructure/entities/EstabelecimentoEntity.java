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
    @Table(name= "Estabelecimento")
    public class EstabelecimentoEntity implements Serializable {

        @Serial
        private static final Long serialVersionUID = 1L;


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID idEstabelecimento;

        @Column(nullable = false, length = 200)
        private String nome;

        @Column(nullable = false, length = 15)
        private String CNPJ;

        @Column(nullable = false, length = 100)
        private String tipoCozinha;

        @Column(nullable = false, length = 200)
        private String tipoEstabelecimento;

        @Column(nullable = false)
        private String HorarioAberturaDiaSemana;

        @Column(nullable = false)
        private String HorarioFechamentoDiaSemana;

        @Column(nullable = false)
        private String HorarioAberturaFeriadoFimSemana;

        @Column(nullable = false)
        private String HorarioFechamentoFeriadoFimSemana;

        @Column()
        private LocalDateTime dataHoraCadastro;


        @ManyToOne
        @JoinColumn(name = "id_endereco")
        private EnderecoEntity enderecoEntity;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @ManyToMany(mappedBy = "estabelecimentoEntities", fetch = FetchType.LAZY)
        private Set<DonoEntity> donoEntities = new HashSet<>();


        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @ManyToMany(mappedBy = "estabelecimentoEntities", fetch = FetchType.LAZY)
        private Set<CardapioEntity> cardapioEntities = new HashSet<>();

        public UUID getIdEstabelecimento() {
            return idEstabelecimento;
        }

        public void setIdEstabelecimento(UUID idEstabelecimento) {
            this.idEstabelecimento = idEstabelecimento;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCNPJ() {
            return CNPJ;
        }

        public void setCNPJ(String CNPJ) {
            this.CNPJ = CNPJ;
        }

        public String getTipoCozinha() {
            return tipoCozinha;
        }

        public void setTipoCozinha(String tipoCozinha) {
            this.tipoCozinha = tipoCozinha;
        }

        public String getTipoEstabelecimento() {
            return tipoEstabelecimento;
        }

        public void setTipoEstabelecimento(String tipoEstabelecimento) {
            this.tipoEstabelecimento = tipoEstabelecimento;
        }

        public String getHorarioAberturaDiaSemana() {
            return HorarioAberturaDiaSemana;
        }

        public void setHorarioAberturaDiaSemana(String horarioAberturaDiaSemana) {
            HorarioAberturaDiaSemana = horarioAberturaDiaSemana;
        }

        public String getHorarioFechamentoDiaSemana() {
            return HorarioFechamentoDiaSemana;
        }

        public void setHorarioFechamentoDiaSemana(String horarioFechamentoDiaSemana) {
            HorarioFechamentoDiaSemana = horarioFechamentoDiaSemana;
        }

        public String getHorarioAberturaFeriadoFimSemana() {
            return HorarioAberturaFeriadoFimSemana;
        }

        public void setHorarioAberturaFeriadoFimSemana(String horarioAberturaFeriadoFimSemana) {
            HorarioAberturaFeriadoFimSemana = horarioAberturaFeriadoFimSemana;
        }

        public String getHorarioFechamentoFeriadoFimSemana() {
            return HorarioFechamentoFeriadoFimSemana;
        }

        public void setHorarioFechamentoFeriadoFimSemana(String horarioFechamentoFeriadoFimSemana) {
            HorarioFechamentoFeriadoFimSemana = horarioFechamentoFeriadoFimSemana;
        }

        public LocalDateTime getDataHoraCadastro() {
            return dataHoraCadastro;
        }

        public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
            this.dataHoraCadastro = dataHoraCadastro;
        }

        public EnderecoEntity getEnderecoEntity() {
            return enderecoEntity;
        }

        public void setEnderecoEntity(EnderecoEntity enderecoEntity) {
            this.enderecoEntity = enderecoEntity;
        }

        public Set<DonoEntity> getDonoEntities() {
            return donoEntities;
        }

        public void setDonoEntities(Set<DonoEntity> donoEntities) {
            this.donoEntities = donoEntities;
        }

        public Set<CardapioEntity> getCardapioEntities() {
            return cardapioEntities;
        }

        public void setCardapioEntities(Set<CardapioEntity> cardapioEntities) {
            this.cardapioEntities = cardapioEntities;
        }

    }
