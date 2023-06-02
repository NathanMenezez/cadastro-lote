package br.com.vita.academy.api.apicadastrolote.repository;

import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UsuarioCustomRespository {
    private final EntityManager em;

    public UsuarioCustomRespository(EntityManager em) {
        this.em = em;
    }

    public Page<Usuario> find(Long id, String nome, String status, Pageable pageable){
        String query = "select U from Usuario as U ";
        String condicao = "where";

        if(id != null){
            query += condicao + " U.id = :id";
            condicao = " and";
        }

        if(nome != null){
            query += condicao + " U.nome = :nome";
            condicao = " and";
        }

        if(status != null){
            query += condicao + " U.status = :status";
            condicao = " and";
        }

        Query q = em.createQuery(query, Usuario.class);

        if(id != null){
            q.setParameter("id", id);
        }

        if(nome != null){
            q.setParameter("nome", nome);
        }

        if(status != null){
            q.setParameter("status", status);
        }
        int total = q.getResultList().size();

        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        List<Usuario> usuarios = q.getResultList();

        return new PageImpl<>(usuarios, pageable, total);
    }
}
