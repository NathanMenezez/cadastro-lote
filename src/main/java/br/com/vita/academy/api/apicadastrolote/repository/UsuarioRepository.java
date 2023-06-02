package br.com.vita.academy.api.apicadastrolote.repository;

import br.com.vita.academy.api.apicadastrolote.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailAndAndStatus(String email, String status);
}
