package br.com.fiap.gestaoestabelecimento.usecases.dono;

import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.services.DonoService;
import org.springframework.stereotype.Component;

@Component
public class ExcluirDonoUseCase {

    private final DonoService donoService;

    public ExcluirDonoUseCase(DonoService donoService) {
        this.donoService = donoService;
    }

    public void executar(String email) {
        donoService.delete(email, TipoCliente.DONO.getCodigo());
    }
}