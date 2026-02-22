package com.tiendasara.models;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Entidad JPA que mapea a la tabla "Productos".
 * Relaciones ManyToOne hacia Categorias y Marcas con carga EAGER (formularios Thymeleaf).
 */
@Entity
@Table(name = "Productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "Precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "Cantidad", nullable = false)
    private int cantidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdCategoria", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdMarca", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Mark mark;

    // ── Constructors ─────────────────────────────────────────

    public Product() {
    }

    public Product(UUID id, String descripcion, BigDecimal precio, int cantidad,
                   Category category, Mark mark) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.category = category;
        this.mark = mark;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
