package br.com.fiap.gestaoestabelecimento.adapters.controllers;

import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.domain.Usuario;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.application.ClienteUseCase;
import br.com.fiap.gestaoestabelecimento.dtos.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes/v1")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    public ClienteController(
            ClienteUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List> buscarPorNome(@RequestParam String nome) {
        Iterable<Cliente> cliente = clienteUseCase.buscarClienteNomeUseCase(nome);
        return ResponseEntity.ok(Collections.singletonList(cliente));
    }

    @GetMapping("/buscar-por-email")
    public ResponseEntity<Cliente> buscarPorEmail(@RequestParam String email) {
        Cliente cliente = clienteUseCase.buscarClienteEmailUseCase(email);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<Cliente> buscarPorId(@RequestParam UUID Idcliente) {
        Cliente cliente = clienteUseCase.buscarClienteIdUseCase(Idcliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/lista")
    public ResponseEntity<List>lista() {
        Iterable<Cliente> clientes = clienteUseCase.listarTodosClientesUseCase();
        return ResponseEntity.ok(Collections.singletonList(clientes));
    }

    @GetMapping("/validar-acesso")
    public ResponseEntity<String> validarAcesso(@RequestBody ValidaUsuarioDTO validaUsuarioDTO) {
        clienteUseCase.validaAcesso(validaUsuarioDTO);
        return ResponseEntity.ok("Acesso válido");
    }

    @PutMapping("/atualizar-login-senha")
    public ResponseEntity<String> atualizarLoginSenha(@RequestBody AtualizaLoginSenhaDTO atualizaLoginSenhaDTO) {
        clienteUseCase.atualizaLogin(atualizaLoginSenhaDTO);
        return ResponseEntity.ok("Login e senha atualizados com sucesso");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody AtualizaClienteDTO atualizaClienteDTO) {
        clienteUseCase.atualizarClienteUserCase(atualizaClienteDTO);
        return ResponseEntity.ok("Dados de cliente atualizados com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiClienteDTO incluiClienteDTO) {
        clienteUseCase.incluirClienteUseCase(incluiClienteDTO);
        return ResponseEntity.ok("Cliente incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam String email) {
        clienteUseCase.deletarClientePorEmailUseCase(email);
        return ResponseEntity.ok("Cliente incluído com sucesso");
    }
}
