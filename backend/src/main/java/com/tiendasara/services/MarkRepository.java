package com.tiendasara.services;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendasara.models.Mark;

/**
 * Repositorio JPA para la entidad Mark.
 * Spring Data genera automáticamente las implementaciones CRUD en tiempo de ejecución.
 */
@Repository
public interface MarkRepository extends JpaRepository<Mark, UUID> {
}
