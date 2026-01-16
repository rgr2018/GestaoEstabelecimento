package br.com.fiap.gestaoestabelecimento.adapters.gateways;
import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.interfaces.EstabelecimentoGateway;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.EnderecoMapper;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.EstabelecimentoMapper;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EnderecoRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EstabelecimentoGatewayImp implements EstabelecimentoGateway {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EnderecoRepository enderecoRepository;

    public EstabelecimentoGatewayImp(
            EstabelecimentoRepository estabelecimentoRepository,
            EnderecoRepository enderecoRepository
    ) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------
    @Transactional
    public Estabelecimento saveEstabelecimento(IncluiEstabelecimentoDTO input) {

        EnderecoEntity endereco = EnderecoMapper.toEntity(input.endereco());
        enderecoRepository.save(endereco);

        EstabelecimentoEntity estabelecimento = EstabelecimentoMapper.toEntity(input);

        estabelecimento.setEnderecoEntity(endereco);
        estabelecimento.setCardapioEntities(new HashSet<>());

        estabelecimentoRepository.save(estabelecimento);

        return EstabelecimentoMapper.toDomain(estabelecimento);
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    @Transactional
    public void updateEstabelecimento(AtualizaEstabelecimentoDTO input) {

        EstabelecimentoEntity estabelecimentoEntity = estabelecimentoRepository
                .findById(input.idEstabelecimento())
                .orElseThrow(() -> new BusinessException("Estabelecimento não cadastrado"));

        EnderecoEntity endereco = enderecoRepository
                .findById(input.endereco().idEndereco())
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));


        EnderecoMapper.toEntityAtualiza(endereco, input.endereco());
        EstabelecimentoMapper.toEntityAtualiza(estabelecimentoEntity, input);

    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------
    public void deleteEstabelecimento(UUID id) {
        EstabelecimentoEntity estabelecimento = estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException("Estabelecimento não cadastrado"));

        estabelecimentoRepository.delete(estabelecimento);
    }

    // -------------------------------------------------------------------------
    // GET
    // -------------------------------------------------------------------------
    public Estabelecimento getEstabelecimento(UUID id) {

        if (id == null) {
            throw new BusinessException("O campo idEstabelecimento está nulo");
        }

        EstabelecimentoEntity estabelecimentoEntity = estabelecimentoRepository.buscaPorId(id);

        if (estabelecimentoEntity == null) {
            throw new BusinessException("Nenhum cliente encontrado com nome " + id);
        }

        return EstabelecimentoMapper.toDomain(estabelecimentoEntity);


    }

    @Override
    public List<Estabelecimento> getAllEstabelecimento() {
        return estabelecimentoRepository.listaEstabelecimentos()
                .stream()
                .map(EstabelecimentoMapper::toDomain)
                .toList();
    }

    // -------------------------------------------------------------------------
    // BUSCAR POR NOME
    // -------------------------------------------------------------------------
    @Override
    public List<Estabelecimento> buscaPorNome(String nome) {

        if (nome == null) {
            throw new BusinessException("O campo nome está nulo");
        }

        return estabelecimentoRepository.buscaPorNome(nome).stream()
                .map(EstabelecimentoMapper::toDomain)
                .toList();
    }

}