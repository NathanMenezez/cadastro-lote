package br.com.vita.academy.api.apicadastrolote.controller;


import br.com.vita.academy.api.apicadastrolote.model.dto.PedidoDTO;
import br.com.vita.academy.api.apicadastrolote.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarPedido(@RequestBody @Valid PedidoDTO pedidoDTO){
        return service.cadastrar(pedidoDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarPedidos(
            @PageableDefault(size = 10) Pageable paginacao,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String data,
            @RequestParam(required = false) Double totalPedido,
            @RequestParam(required = false) Long idProduto,
            @RequestParam(required = false) Long idUsuario
    ){
        return service.listarPedidos(paginacao, id, data, totalPedido, idProduto, idUsuario);
    }
}
