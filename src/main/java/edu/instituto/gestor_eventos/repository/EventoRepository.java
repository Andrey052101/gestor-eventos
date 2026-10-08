package edu.instituto.gestoreventos.repository;

import edu.instituto.gestoreventos.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Buscar por nombre con paginación
    Page<Evento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Buscar por categoría con paginación
    Page<Evento> findByCategoriaId(Long categoriaId, Pageable pageable);
}