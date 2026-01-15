package br.com.fiap.gestaoestabelecimento.adapters.gateways;
import br.com.fiap.gestaoestabelecimento.domain.Dono;
import br.com.fiap.gestaoestabelecimento.domain.DonoGateway;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DonoGatewayImp implements DonoGateway {

    private final DonoRepository donoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    public DonoGatewayImp(
            DonoRepository donoRepository,
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository,
            EstabelecimentoRepository estabelecimentoRepository
    ) {
        this.donoRepository = donoRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public List<Dono> buscarDonoPorNome(String nome) {
        List<DonoEntity> lista =  donoRepository.buscarDonosPorNome(nome, "DONO");
        if (lista.isEmpty()) {
            throw new BusinessException("Nenhum usuário encontrado com nome " + nome);
        }
        return lista.stream()
                .map(DonoMapper::toDomain)
                .toList();
    }

    public Dono buscarDonoPorEmail(String email) {

        DonoEntity donoEntity =  donoRepository.buscarDonoPorEmail(email,"DONO");
        if (donoEntity == null){
            throw new  BusinessException("Usuario não existe");
        }
        Dono donoMapper = DonoMapper.toDomain(donoEntity);
        return donoMapper;
    }

    @Transactional
    public Dono saveDono(IncluiDonoDTO input) {

        DonoEntity validaEmail = donoRepository.buscarDonoPorEmail(input.usuarioDTO().email(),"DONO");
        if (validaEmail != null) {
            throw new ValidationException("E-mail já cadastrado. Cadastrar outro e-mail.");
        }
        EstabelecimentoEntity estabelecimento = estabelecimentoRepository.findById(input.idEstabelecimento())
                .orElseThrow(() -> new BusinessException("Estabelecimento não encontrado"));

        EnderecoEntity enderecoEntity = EnderecoMapper.toEntity(input.usuarioDTO().endereco());
        enderecoRepository.save(enderecoEntity);

        UsuarioEntity usuarioEntity = UsuarioMapper.toEntity(input.usuarioDTO(), "DONO");
        usuarioEntity.setEnderecoEntity(enderecoEntity);
        usuarioEntity.setDataUltimaAlteracao(String.valueOf(LocalDateTime.now()));
        usuarioRepository.save(usuarioEntity);

        DonoEntity donoEntity = DonoMapper.toEntity(input, estabelecimento);

        donoEntity.setUsuarioEntity(usuarioEntity);
        donoEntity.setDataHoraCadastro(LocalDateTime.now());
        donoRepository.save(donoEntity);

        Dono dono = DonoMapper.toDomain(donoEntity);
        return dono;
    }

    @Transactional
    public Dono updateDono(AtualizaDonoDTO input) {

        if (input.usuarioDTO() == null) {
            throw new BusinessException("O campo usuarioDTO não pode ser nulo");
        }

        if (input.usuarioDTO().endereco() == null) {
            throw new BusinessException("O campo endereco dentro de usuarioDTO não pode ser nulo");
        }

        DonoEntity donoEntity = donoRepository.findById(input.idDono())
                .orElseThrow(() -> new BusinessException("Dono não encontrado"));

        UsuarioEntity usuarioEntity = usuarioRepository.findById(input.usuarioDTO().idUsuario())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        EnderecoEntity enderecoEntity= enderecoRepository.findById(input.usuarioDTO().endereco().idEndereco())
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        EstabelecimentoEntity estabelecimentoEntity= estabelecimentoRepository.findById(input.idEstabelecimento())
                .orElseThrow(() -> new BusinessException("Estabelecimento não encontrado"));


       EnderecoEntity endereco = EnderecoMapper.toEntityAtualiza(enderecoEntity, input.usuarioDTO().endereco());
       UsuarioEntity usuario = UsuarioMapper.toEntityAtualiza(usuarioEntity,endereco, input.usuarioDTO(), "DONO");
       DonoMapper.toEntityAtualiza(donoEntity, estabelecimentoEntity, usuario);

        return DonoMapper.toDomain(donoEntity);
    }

    public Dono getDono(UUID idDono) {

        if (idDono == null) {
            throw new BusinessException("O campo idDono está nulo");
        }

        DonoEntity donoEntity = donoRepository.buscaDonoPorId(idDono);
        if (donoEntity == null){
            throw new  BusinessException("Usuario não existe");
        }
        return DonoMapper.toDomain(donoEntity);
    }

    @Override
    public Iterable<Dono> listaTodosDonos() {
        List<DonoEntity> donos = donoRepository.listaTodosDonos();

        if (donos.isEmpty()) {
            throw new BusinessException("Nenhum dono encontrado");
        }

        return donos.stream()
                .map(DonoMapper::toDomain)
                .toList();
    }


    @Transactional
    public void deleteDono(String email) {

        if (email== null) {
            throw new BusinessException("O campo usuarioDTO não pode ser nulo");
        }

        DonoEntity validaEmail = donoRepository.buscarDonoPorEmail(email, "Dono");

        if (validaEmail == null) {
            throw new ValidationException("Email não encontrado");
        }


        UUID id_usuario = usuarioRepository.buscaIdPorEmail(email,"DONO");
        UUID id_endereco = usuarioRepository.buscaIdEnderecoPorEmail(email);
        UUID id_dono = usuarioRepository.buscaIddonoPoridUsuario(id_usuario);

        enderecoRepository.deleteById(id_endereco);
        donoRepository.deleteById(id_dono);
        usuarioRepository.deleteById(id_usuario);

    }
}