package br.com.vita.academy.api.apicadastrolote.repository;

import br.com.vita.academy.api.apicadastrolote.model.entities.Pedido;
import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class PedidoCustomRepository {

    private final EntityManager em;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PedidoCustomRepository(EntityManager em) {
        this.em = em;
    }

    public Page<Pedido> find(Long id, String dataPedido, Double totalPedido, Long idProduto, Long idUsuario,Pageable pageable){
        String query = "select P from Pedido as P ";
        String condicao = "where";
        Date data = null;


        if(id != null){
            query += condicao + " P.id = :id";
            condicao = " and";
        }

        if(dataPedido != null){

            try {
                data = sdf.parse(dataPedido);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            query += condicao + " P.dataPedido = :data";
            condicao = " and";
        }

        if(totalPedido != null){
            query += condicao + " P.totalPedido = :totalPedido";
            condicao = " and";
        }

        if(idProduto != null){
            query += condicao + " P.idProduto = :idProduto";
            condicao = " and";
        }

        Usuario usuario = null;

        if(idUsuario != null){
            usuario = usuarioRepository.findById(idUsuario).get();
            query += condicao + " P.idUsuario = :usuario";
            condicao = " and";
        }



        Query q = em.createQuery(query, Usuario.class);

        if(id != null){
            q.setParameter("id", id);
        }

        if(dataPedido != null){
            q.setParameter("data", data);
        }

        if(totalPedido != null){
            q.setParameter("totalPedido", totalPedido);
        }

        if(idProduto != null){
            q.setParameter("idProduto", idProduto);
        }

        if(idUsuario != null){
            q.setParameter("usuario", usuario);
        }

        int total = q.getResultList().size();

        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        List<Pedido> pedidos = q.getResultList();

        return new PageImpl<>(pedidos, pageable, total);
    }
}
