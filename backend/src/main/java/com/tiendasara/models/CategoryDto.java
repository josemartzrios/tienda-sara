package com.tiendasara.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para transferencia de datos de Categoría.
 * Separa la capa de presentación de la entidad JPA (principio de responsabilidad única).
 */
public class CategoryDto {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    private String descripcion;

    // ── Getters & Setters ────────────────────────────────────

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
