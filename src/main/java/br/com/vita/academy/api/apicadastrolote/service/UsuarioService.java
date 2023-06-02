package br.com.vita.academy.api.apicadastrolote.service;


import br.com.vita.academy.api.apicadastrolote.model.dto.CadastroUsuariosRetorno;
import br.com.vita.academy.api.apicadastrolote.model.dto.DadosAtualizacaoUsuarioDTO;
import br.com.vita.academy.api.apicadastrolote.model.dto.UsuarioDto;
import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import br.com.vita.academy.api.apicadastrolote.repository.UsuarioCustomRespository;
import br.com.vita.academy.api.apicadastrolote.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioCustomRespository customRespository;

    public ResponseEntity<?> cadastrarUsuarios(List<UsuarioDto> usuarios) {
        List<Usuario> usuariosUnicos = new ArrayList<>();
        List<UsuarioDto> usuariosDuplicados = new ArrayList<>();

        for (UsuarioDto usuario :usuarios) {
            if(repository.existsByEmailAndAndStatus(usuario.getEmail(), "ATIVO")){
                usuariosDuplicados.add(usuario);
            }else if(!repository.existsByEmailAndAndStatus(usuario.getEmail(), "ATIVO")){
                Usuario usuarioModel = new Usuario();
                BeanUtils.copyProperties(usuario, usuarioModel);
                usuariosUnicos.add(usuarioModel);
            }
        }

        repository.saveAll(usuariosUnicos);

        if(usuariosDuplicados.size() > 0 && usuariosUnicos.size() > 0){
            return ResponseEntity.status(200).body(new CadastroUsuariosRetorno(usuariosUnicos, usuariosDuplicados));
        }

        if(usuariosUnicos.size() == 0){
            return ResponseEntity.status(400).body("Todos os usuarios são duplicados!");
        }

        return ResponseEntity.status(200).body(usuariosUnicos.size() + " Usuarios cadastrado com sucesso!");
    }

    public ResponseEntity<?> listarUsuarios(Long id, String nome, String status, Pageable pageable) {
        return ResponseEntity.status(200).body(customRespository.find(id, nome, status, pageable));
    }

    public ResponseEntity<?> deletarUsuario(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.get().getStatus().equals("INATIVO")){
            return ResponseEntity.status(400).body("Usuario já inativo");
        }
        usuario.get().setStatus("INATIVO");
        repository.save(usuario.get());
        return ResponseEntity.status(200).body("Usuario inativo com sucesso!");
    }

    public ResponseEntity<?> atualizarUsuario(Long id, DadosAtualizacaoUsuarioDTO dados) {
        if(!repository.existsById(id)){
            return ResponseEntity.status(404).body("Usuario não encontrado!");
        }

        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.get().getStatus().equals("INATIVO")){
            return ResponseEntity.status(400).body("Usuario inativo");
        }

        if(dados.getEmail() == null){
            dados.setEmail(usuario.get().getEmail());
        }

        if(dados.getNome() == null){
            dados.setEmail(usuario.get().getNome());
        }

        BeanUtils.copyProperties(dados, usuario.get());

        repository.save(usuario.get());
        return ResponseEntity.status(200).body("Usuario Alterado com Sucesso!");

    }
}
