package edu.instituto.gestor_eventos.repository;

import edu.instituto.gestor_eventos.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
    Page<Evento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable); // Le dice a la BD cuentos traer y desde su posicion
    Page<Evento> findByCategoriaId(Long categoriaId, Pageable pageable);
} //Tare todos los eventos, busca los eventos