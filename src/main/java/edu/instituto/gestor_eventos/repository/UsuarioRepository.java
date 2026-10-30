package edu.instituto.gestor_eventos.repository;

import edu.instituto.gestor_eventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByNombre(String nombre);
    boolean existsByCorreo(String correo);
} //Busca al usuario en la base de datos y compara atributos, registra pero antes mira que no exista