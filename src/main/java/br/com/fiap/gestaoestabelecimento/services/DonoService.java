package br.com.fiap.gestaoestabelecimento.services;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.entities.Dono;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.repositories.DonoRepository;
import br.com.fiap.gestaoestabelecimento.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;


@Service
public class DonoService extends UsuarioService<Dono> {

    public DonoService(DonoRepository donoRepository) {
        super((UsuarioRepository<Dono>) donoRepository);


        this.donoRepository = donoRepository;
    }
    private final DonoRepository donoRepository;

    public int salvarDono(IncluiDonoDTO dono, Long id) {
        int result = this.donoRepository.salvaDono(dono, id);
        if (result == 0) throw new RuntimeException("Erro ao Salvar cliente");
        return result;
    }

    public void atualizaDono(Dono dono, long id) {
        int result = this.donoRepository.atualizaDetalhesDono(dono, id);
        if (result == 0) throw new RuntimeException("Usuário não encontrado para atualização");
    }


    public void salvarDonoUsuario(IncluiDonoDTO incluiDonoDTO, Long id, String tipoUsuario) {
        int saveUsuario = donoRepository.salvarDonoUsuario(incluiDonoDTO, id, tipoUsuario);
        if (saveUsuario != 1) {
            throw new BusinessException("Erro ao salvar usuário: " + incluiDonoDTO.getEmail());
        }
    }
}