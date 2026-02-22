-- ============================================================
-- SEED DATA: 5 registros por tabla
-- Usa MERGE para evitar duplicados al reiniciar
-- ============================================================

-- ── CATEGORIAS ───────────────────────────────────────────────
MERGE INTO Categorias AS target
USING (VALUES
    ('A1B2C3D4-1111-1111-1111-000000000001', 'Electrónica'),
    ('A1B2C3D4-1111-1111-1111-000000000002', 'Ropa'),
    ('A1B2C3D4-1111-1111-1111-000000000003', 'Hogar'),
    ('A1B2C3D4-1111-1111-1111-000000000004', 'Deportes'),
    ('A1B2C3D4-1111-1111-1111-000000000005', 'Alimentos')
) AS source (Id, Descripcion)
ON target.Id = CAST(source.Id AS UNIQUEIDENTIFIER)
WHEN NOT MATCHED THEN
    INSERT (Id, Descripcion) VALUES (CAST(source.Id AS UNIQUEIDENTIFIER), source.Descripcion);

-- ── MARCAS ───────────────────────────────────────────────────
MERGE INTO Marcas AS target
USING (VALUES
    ('B2C3D4E5-2222-2222-2222-000000000001', 'Samsung'),
    ('B2C3D4E5-2222-2222-2222-000000000002', 'Nike'),
    ('B2C3D4E5-2222-2222-2222-000000000003', 'LG'),
    ('B2C3D4E5-2222-2222-2222-000000000004', 'Adidas'),
    ('B2C3D4E5-2222-2222-2222-000000000005', 'Sony')
) AS source (Id, Descripcion)
ON target.Id = CAST(source.Id AS UNIQUEIDENTIFIER)
WHEN NOT MATCHED THEN
    INSERT (Id, Descripcion) VALUES (CAST(source.Id AS UNIQUEIDENTIFIER), source.Descripcion);

-- ── PRODUCTOS ────────────────────────────────────────────────
MERGE INTO Productos AS target
USING (VALUES
    ('C3D4E5F6-3333-3333-3333-000000000001', 'Televisor 55 pulgadas', 12999.99, 10,
     'A1B2C3D4-1111-1111-1111-000000000001', 'B2C3D4E5-2222-2222-2222-000000000001'),
    ('C3D4E5F6-3333-3333-3333-000000000002', 'Tenis deportivos', 1899.50, 25,
     'A1B2C3D4-1111-1111-1111-000000000004', 'B2C3D4E5-2222-2222-2222-000000000002'),
    ('C3D4E5F6-3333-3333-3333-000000000003', 'Refrigerador doble puerta', 18500.00, 5,
     'A1B2C3D4-1111-1111-1111-000000000003', 'B2C3D4E5-2222-2222-2222-000000000003'),
    ('C3D4E5F6-3333-3333-3333-000000000004', 'Sudadera deportiva', 1250.00, 30,
     'A1B2C3D4-1111-1111-1111-000000000002', 'B2C3D4E5-2222-2222-2222-000000000004'),
    ('C3D4E5F6-3333-3333-3333-000000000005', 'Audífonos inalámbricos', 3499.99, 15,
     'A1B2C3D4-1111-1111-1111-000000000001', 'B2C3D4E5-2222-2222-2222-000000000005')
) AS source (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
ON target.Id = CAST(source.Id AS UNIQUEIDENTIFIER)
WHEN NOT MATCHED THEN
    INSERT (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
    VALUES (CAST(source.Id AS UNIQUEIDENTIFIER), source.Descripcion, source.Precio, source.Cantidad,
            CAST(source.IdCategoria AS UNIQUEIDENTIFIER), CAST(source.IdMarca AS UNIQUEIDENTIFIER));

-- ── CARRITO ──────────────────────────────────────────────────
MERGE INTO Carrito AS target
USING (VALUES
    ('D4E5F6A7-4444-4444-4444-000000000001', 'VTA-001', 14899.49, 'Pagado', '2026-02-01'),
    ('D4E5F6A7-4444-4444-4444-000000000002', 'VTA-002', 3799.00, 'Pagado', '2026-02-03'),
    ('D4E5F6A7-4444-4444-4444-000000000003', 'VTA-003', 18500.00, 'Pendiente', '2026-02-05'),
    ('D4E5F6A7-4444-4444-4444-000000000004', 'VTA-004', 6999.98, 'Cancelado', '2026-02-07'),
    ('D4E5F6A7-4444-4444-4444-000000000005', 'VTA-005', 2500.00, 'Pagado', '2026-02-09')
) AS source (Id, FolioVenta, TotalCompra, Estatus, Fecha)
ON target.Id = CAST(source.Id AS UNIQUEIDENTIFIER)
WHEN NOT MATCHED THEN
    INSERT (Id, FolioVenta, TotalCompra, Estatus, Fecha)
    VALUES (CAST(source.Id AS UNIQUEIDENTIFIER), source.FolioVenta, source.TotalCompra, source.Estatus, CAST(source.Fecha AS DATETIME));

-- ── CARRITO DETALLE ──────────────────────────────────────────
MERGE INTO CarritoDetalle AS target
USING (VALUES
    ('E5F6A7B8-5555-5555-5555-000000000001', 'D4E5F6A7-4444-4444-4444-000000000001', 'C3D4E5F6-3333-3333-3333-000000000001', 1, 12999.99),
    ('E5F6A7B8-5555-5555-5555-000000000002', 'D4E5F6A7-4444-4444-4444-000000000001', 'C3D4E5F6-3333-3333-3333-000000000002', 1, 1899.50),
    ('E5F6A7B8-5555-5555-5555-000000000003', 'D4E5F6A7-4444-4444-4444-000000000002', 'C3D4E5F6-3333-3333-3333-000000000002', 2, 3799.00),
    ('E5F6A7B8-5555-5555-5555-000000000004', 'D4E5F6A7-4444-4444-4444-000000000003', 'C3D4E5F6-3333-3333-3333-000000000003', 1, 18500.00),
    ('E5F6A7B8-5555-5555-5555-000000000005', 'D4E5F6A7-4444-4444-4444-000000000004', 'C3D4E5F6-3333-3333-3333-000000000005', 2, 6999.98)
) AS source (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
ON target.Id = CAST(source.Id AS UNIQUEIDENTIFIER)
WHEN NOT MATCHED THEN
    INSERT (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
    VALUES (CAST(source.Id AS UNIQUEIDENTIFIER), CAST(source.IdCarrito AS UNIQUEIDENTIFIER),
            CAST(source.IdProducto AS UNIQUEIDENTIFIER), source.Cantidad, source.Subtotal);
