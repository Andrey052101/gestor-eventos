package edu.instituto.gestor_eventos.repository;

import edu.instituto.gestor_eventos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}