package br.com.vita.academy.api.apicadastrolote.repository;

import br.com.vita.academy.api.apicadastrolote.model.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
