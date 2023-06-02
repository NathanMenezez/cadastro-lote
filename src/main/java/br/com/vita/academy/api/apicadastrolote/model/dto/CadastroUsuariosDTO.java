package br.com.vita.academy.api.apicadastrolote.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CadastroUsuariosDTO {

    @NotNull
    private List<@Valid UsuarioDto> usuarios;
}
