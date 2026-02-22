package com.tiendasara.models;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.*;

/**
 * DTO para transferencia de datos de Producto.
 * Contiene validaciones de entrada y los IDs de las relaciones (categoría y marca).
 */
public class ProductDto {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int cantidad;

    @NotNull(message = "La categoría es obligatoria")
    private UUID idCategoria;

    @NotNull(message = "La marca es obligatoria")
    private UUID idMarca;

    // ── Getters & Setters ────────────────────────────────────

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

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
    }

    public UUID getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(UUID idMarca) {
        this.idMarca = idMarca;
    }
}
