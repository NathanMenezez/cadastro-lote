package br.com.vita.academy.api.apicadastrolote.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDto {
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
