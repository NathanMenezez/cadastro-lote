package br.com.vita.academy.api.apicadastrolote.model.dto;

import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CadastroUsuariosRetorno {
    private List<Usuario> usuariosUnicos;
    private List<UsuarioDto> usuariosDuplicados;
}
