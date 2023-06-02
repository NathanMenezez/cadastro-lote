package br.com.vita.academy.api.apicadastrolote.service;

import br.com.vita.academy.api.apicadastrolote.model.dto.PedidoDTO;
import br.com.vita.academy.api.apicadastrolote.model.entities.Pedido;
import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import br.com.vita.academy.api.apicadastrolote.repository.PedidoCustomRepository;
import br.com.vita.academy.api.apicadastrolote.repository.PedidoRepository;
import br.com.vita.academy.api.apicadastrolote.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository repository;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PedidoCustomRepository customRepository;

    public ResponseEntity<?> cadastrar(PedidoDTO pedidoDTO) {
        if(!userRepository.existsById(pedidoDTO.getIdUsuario())){
            return ResponseEntity.status(404).body("Usuario não cadastrado!");
        }
        Usuario usuario = userRepository.findById(pedidoDTO.getIdUsuario()).get();
        if(usuario.getStatus().equals("INATIVO")){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario Inativo");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        BeanUtils.copyProperties(pedidoDTO, pedido);

        repository.save(pedido);

        return ResponseEntity.status(201).body(pedido);

    }

    public ResponseEntity<?> listarPedidos(Pageable paginacao, Long id, String data, Double totalPedido, Long idProduto, Long idUsuario) {
        return ResponseEntity.status(200).body(customRepository.find(id, data, totalPedido, idProduto, idUsuario, paginacao));
    }
}
