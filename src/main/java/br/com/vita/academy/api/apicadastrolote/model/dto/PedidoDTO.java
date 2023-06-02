package br.com.vita.academy.api.apicadastrolote.model.dto;

import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoDTO {

    @NotNull
    private Double totalPedido;

    @NotNull
    private Long idProduto;

    @NotNull
    private Long idUsuario;
}
