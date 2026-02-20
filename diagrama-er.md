# Diagrama Entidad-Relación

## Modelo de datos del sistema de carrito de compras

```mermaid
erDiagram
    CATEGORIAS {
        int Id PK
        varchar Descripcion
    }

    MARCAS {
        int Id PK
        varchar Descripcion
    }

    PRODUCTOS {
        int Id PK
        varchar Descripcion
        decimal Precio
        int Cantidad
        int idCategoria FK
        int idMarca FK
    }

    CARRITO {
        int Id PK
        varchar FolioVenta
        decimal TotalCompra
        varchar Estatus
        datetime Fecha
    }

    CARRITODETALLE {
        int Id PK
        int IdCarrito FK
        int IdProducto FK
        int Cantidad
        decimal Subtotal
    }

    CATEGORIAS ||--o{ PRODUCTOS : "tiene"
    MARCAS ||--o{ PRODUCTOS : "tiene"
    CARRITO ||--o{ CARRITODETALLE : "contiene"
    PRODUCTOS ||--o{ CARRITODETALLE : "aparece en"
```

## Relaciones

| Relación | Tipo | Descripción |
|---|---|---|
| **CATEGORIAS → PRODUCTOS** | 1 a muchos | Una categoría tiene muchos productos |
| **MARCAS → PRODUCTOS** | 1 a muchos | Una marca tiene muchos productos |
| **CARRITO → CARRITODETALLE** | 1 a muchos | Un carrito tiene muchas líneas de detalle |
| **PRODUCTOS → CARRITODETALLE** | 1 a muchos | Un producto puede aparecer en muchos detalles de carrito |
