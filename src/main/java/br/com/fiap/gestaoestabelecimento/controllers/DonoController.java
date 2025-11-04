package br.com.fiap.gestaoestabelecimento.controllers;

import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.usecases.dono.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donos/v1/")
public class DonoController {

    private final BuscarDonoPorNomeUseCase buscarDonoPorNomeUseCase;
    private final ValidarAcessoDonoUseCase validarAcessoDonoUseCase;
    private final AtualizarLoginSenhaDonoUseCase atualizarLoginSenhaDonoUseCase;
    private final IncluirDonoUseCase incluirDonoUseCase;
    private final ExcluirDonoUseCase excluirDonoUseCase;

    public DonoController(
            BuscarDonoPorNomeUseCase buscarDonoPorNomeUseCase,
            ValidarAcessoDonoUseCase validarAcessoDonoUseCase,
            AtualizarLoginSenhaDonoUseCase atualizarLoginSenhaDonoUseCase,
            IncluirDonoUseCase incluirDonoUseCase,
            ExcluirDonoUseCase excluirDonoUseCase
    ) {
        this.buscarDonoPorNomeUseCase = buscarDonoPorNomeUseCase;
        this.validarAcessoDonoUseCase = validarAcessoDonoUseCase;
        this.atualizarLoginSenhaDonoUseCase = atualizarLoginSenhaDonoUseCase;
        this.incluirDonoUseCase = incluirDonoUseCase;
        this.excluirDonoUseCase = excluirDonoUseCase;
    }


    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<UsuarioNameDTO>> buscarPorNome(@RequestParam String nome) {
        List<UsuarioNameDTO> clientes = buscarDonoPorNomeUseCase.executar(nome);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/validar-acesso")
    public ResponseEntity<String> validarAcesso(@RequestBody ValidaUsuarioDTO dto) {
        validarAcessoDonoUseCase.executar(dto.getLogin(), dto.getSenha());
        return ResponseEntity.ok("Acesso válido");
    }

    @PutMapping("/atualizar-login-senha")
    public ResponseEntity<String> atualizarLoginSenha(@RequestBody ValidaUsuarioDTO dto) {
        atualizarLoginSenhaDonoUseCase.executar(dto);
        return ResponseEntity.ok("Login e senha atualizados com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiDonoDTO dto) {
        incluirDonoUseCase.executar(dto);
        return ResponseEntity.ok("Dono incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam String email) {
        excluirDonoUseCase.executar(email);
        return ResponseEntity.ok("Dono excluído com sucesso");
    }
}