package br.com.vita.academy.api.apicadastrolote.controller;

import br.com.vita.academy.api.apicadastrolote.model.dto.CadastroUsuariosDTO;
import br.com.vita.academy.api.apicadastrolote.model.dto.DadosAtualizacaoUsuarioDTO;
import br.com.vita.academy.api.apicadastrolote.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuarios(@RequestBody @Valid CadastroUsuariosDTO usuarios){
        return service.cadastrarUsuarios(usuarios.getUsuarios());
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarUsuario(
            @PageableDefault(size = 10) Pageable paginacao,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String status
    ){
        return service.listarUsuarios(id,nome,status, paginacao);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
        return service.deletarUsuario(id);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody DadosAtualizacaoUsuarioDTO dados){
        return service.atualizarUsuario(id, dados);
    }
}
