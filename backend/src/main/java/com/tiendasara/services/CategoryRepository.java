package com.tiendasara.services;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendasara.models.Category;

/**
 * Repositorio JPA para la entidad Category.
 * Spring Data genera automáticamente las implementaciones CRUD en tiempo de ejecución.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
