package com.tiendasara.models;

import java.util.UUID;

import jakarta.persistence.*;

/**
 * Entidad JPA que mapea a la tabla "Marcas".
 * Utiliza UUID como clave primaria para mayor seguridad y escalabilidad.
 */
@Entity
@Table(name = "Marcas")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Descripcion", nullable = false, length = 100)
    private String descripcion;

    // ── Constructors ─────────────────────────────────────────

    public Mark() {
    }

    public Mark(UUID id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // ── Getters & Setters ────────────────────────────────────

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
