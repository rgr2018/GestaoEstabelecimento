package br.com.fiap.gestaoestabelecimento.usecases.dono;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.services.DonoService;
import org.springframework.stereotype.Component;

@Component
public class IncluirDonoUseCase {

    private final DonoService donoService;

    public IncluirDonoUseCase(DonoService donoService) {
        this.donoService = donoService;
    }

    public void executar(IncluiDonoDTO dto) {
        int existe = donoService.validaExisteEmail(dto.getEmail());
        if (existe == 1) {
            throw new ValidationException("E-mail já cadastrado. Cadastrar outro e-mail.");
        }

        Long ultimoId = donoService.buscaUltimoId();
        donoService.salvarDonoUsuario(dto, ultimoId + 1, TipoCliente.DONO.getCodigo());
        donoService.salvarDono(dto, ultimoId + 1);
    }
}