-- ============================================================
-- PASO 3: Crear la base de datos TiendaSara
-- ============================================================
CREATE DATABASE TiendaSara;
GO

USE TiendaSara;
GO

-- ============================================================
-- PASO 4: Crear tablas con UUID (UNIQUEIDENTIFIER) como PK
-- ============================================================

-- Tabla CATEGORIAS
CREATE TABLE Categorias (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Descripcion VARCHAR(100) NOT NULL
);
GO

-- Tabla MARCAS
CREATE TABLE Marcas (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Descripcion VARCHAR(100) NOT NULL
);
GO

-- Tabla PRODUCTOS
CREATE TABLE Productos (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    Descripcion VARCHAR(200) NOT NULL,
    Precio DECIMAL(10, 2) NOT NULL,
    Cantidad INT NOT NULL DEFAULT 0,
    IdCategoria UNIQUEIDENTIFIER NOT NULL,
    IdMarca UNIQUEIDENTIFIER NOT NULL,
    CONSTRAINT FK_Productos_Categorias FOREIGN KEY (IdCategoria) REFERENCES Categorias(Id),
    CONSTRAINT FK_Productos_Marcas FOREIGN KEY (IdMarca) REFERENCES Marcas(Id)
);
GO

-- Tabla CARRITO
CREATE TABLE Carrito (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    FolioVenta VARCHAR(50) NOT NULL,
    TotalCompra DECIMAL(10, 2) NOT NULL DEFAULT 0,
    Estatus VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    Fecha DATETIME NOT NULL DEFAULT GETDATE()
);
GO

-- Tabla CARRITODETALLE
CREATE TABLE CarritoDetalle (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    IdCarrito UNIQUEIDENTIFIER NOT NULL,
    IdProducto UNIQUEIDENTIFIER NOT NULL,
    Cantidad INT NOT NULL,
    Subtotal DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_CarritoDetalle_Carrito FOREIGN KEY (IdCarrito) REFERENCES Carrito(Id),
    CONSTRAINT FK_CarritoDetalle_Productos FOREIGN KEY (IdProducto) REFERENCES Productos(Id)
);
GO

-- ============================================================
-- PASO 5: Insertar 5 registros en cada tabla
-- Se usan UUIDs fijos para mantener las relaciones entre tablas
-- ============================================================

-- Inserciones en CATEGORIAS
INSERT INTO Categorias (Id, Descripcion) VALUES ('A1B2C3D4-1111-1111-1111-000000000001', 'Electrónica');
INSERT INTO Categorias (Id, Descripcion) VALUES ('A1B2C3D4-1111-1111-1111-000000000002', 'Ropa');
INSERT INTO Categorias (Id, Descripcion) VALUES ('A1B2C3D4-1111-1111-1111-000000000003', 'Hogar');
INSERT INTO Categorias (Id, Descripcion) VALUES ('A1B2C3D4-1111-1111-1111-000000000004', 'Deportes');
INSERT INTO Categorias (Id, Descripcion) VALUES ('A1B2C3D4-1111-1111-1111-000000000005', 'Alimentos');
GO

-- Inserciones en MARCAS
INSERT INTO Marcas (Id, Descripcion) VALUES ('B2C3D4E5-2222-2222-2222-000000000001', 'Samsung');
INSERT INTO Marcas (Id, Descripcion) VALUES ('B2C3D4E5-2222-2222-2222-000000000002', 'Nike');
INSERT INTO Marcas (Id, Descripcion) VALUES ('B2C3D4E5-2222-2222-2222-000000000003', 'LG');
INSERT INTO Marcas (Id, Descripcion) VALUES ('B2C3D4E5-2222-2222-2222-000000000004', 'Adidas');
INSERT INTO Marcas (Id, Descripcion) VALUES ('B2C3D4E5-2222-2222-2222-000000000005', 'Sony');
GO

-- Inserciones en PRODUCTOS
INSERT INTO Productos (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
VALUES ('C3D4E5F6-3333-3333-3333-000000000001', 'Televisor 55 pulgadas', 12999.99, 10,
        'A1B2C3D4-1111-1111-1111-000000000001', 'B2C3D4E5-2222-2222-2222-000000000001');

INSERT INTO Productos (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
VALUES ('C3D4E5F6-3333-3333-3333-000000000002', 'Tenis deportivos', 1899.50, 25,
        'A1B2C3D4-1111-1111-1111-000000000004', 'B2C3D4E5-2222-2222-2222-000000000002');

INSERT INTO Productos (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
VALUES ('C3D4E5F6-3333-3333-3333-000000000003', 'Refrigerador doble puerta', 18500.00, 5,
        'A1B2C3D4-1111-1111-1111-000000000003', 'B2C3D4E5-2222-2222-2222-000000000003');

INSERT INTO Productos (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
VALUES ('C3D4E5F6-3333-3333-3333-000000000004', 'Sudadera deportiva', 1250.00, 30,
        'A1B2C3D4-1111-1111-1111-000000000002', 'B2C3D4E5-2222-2222-2222-000000000004');

INSERT INTO Productos (Id, Descripcion, Precio, Cantidad, IdCategoria, IdMarca)
VALUES ('C3D4E5F6-3333-3333-3333-000000000005', 'Audífonos inalámbricos', 3499.99, 15,
        'A1B2C3D4-1111-1111-1111-000000000001', 'B2C3D4E5-2222-2222-2222-000000000005');
GO

-- Inserciones en CARRITO
INSERT INTO Carrito (Id, FolioVenta, TotalCompra, Estatus, Fecha)
VALUES ('D4E5F6A7-4444-4444-4444-000000000001', 'VTA-001', 14899.49, 'Pagado', '2026-02-01');

INSERT INTO Carrito (Id, FolioVenta, TotalCompra, Estatus, Fecha)
VALUES ('D4E5F6A7-4444-4444-4444-000000000002', 'VTA-002', 3799.00, 'Pagado', '2026-02-03');

INSERT INTO Carrito (Id, FolioVenta, TotalCompra, Estatus, Fecha)
VALUES ('D4E5F6A7-4444-4444-4444-000000000003', 'VTA-003', 18500.00, 'Pendiente', '2026-02-05');

INSERT INTO Carrito (Id, FolioVenta, TotalCompra, Estatus, Fecha)
VALUES ('D4E5F6A7-4444-4444-4444-000000000004', 'VTA-004', 6999.98, 'Cancelado', '2026-02-07');

INSERT INTO Carrito (Id, FolioVenta, TotalCompra, Estatus, Fecha)
VALUES ('D4E5F6A7-4444-4444-4444-000000000005', 'VTA-005', 2500.00, 'Pagado', '2026-02-09');
GO

-- Inserciones en CARRITODETALLE
INSERT INTO CarritoDetalle (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
VALUES (NEWID(), 'D4E5F6A7-4444-4444-4444-000000000001', 'C3D4E5F6-3333-3333-3333-000000000001', 1, 12999.99);

INSERT INTO CarritoDetalle (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
VALUES (NEWID(), 'D4E5F6A7-4444-4444-4444-000000000001', 'C3D4E5F6-3333-3333-3333-000000000002', 1, 1899.50);

INSERT INTO CarritoDetalle (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
VALUES (NEWID(), 'D4E5F6A7-4444-4444-4444-000000000002', 'C3D4E5F6-3333-3333-3333-000000000002', 2, 3799.00);

INSERT INTO CarritoDetalle (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
VALUES (NEWID(), 'D4E5F6A7-4444-4444-4444-000000000003', 'C3D4E5F6-3333-3333-3333-000000000003', 1, 18500.00);

INSERT INTO CarritoDetalle (Id, IdCarrito, IdProducto, Cantidad, Subtotal)
VALUES (NEWID(), 'D4E5F6A7-4444-4444-4444-000000000004', 'C3D4E5F6-3333-3333-3333-000000000005', 2, 6999.98);
GO

-- ============================================================
-- PASO 7: Consultas con INNER JOIN
-- ============================================================

-- CONSULTA 1: Productos con sus marcas y categorías
SELECT 
    p.Id AS ProductoId,
    p.Descripcion AS Producto,
    p.Precio,
    p.Cantidad AS Stock,
    c.Descripcion AS Categoria,
    m.Descripcion AS Marca
FROM Productos p
INNER JOIN Categorias c ON p.IdCategoria = c.Id
INNER JOIN Marcas m ON p.IdMarca = m.Id;

-- CONSULTA 2: Carritos con sus detalles y productos
SELECT 
    ca.Id AS CarritoId,
    ca.FolioVenta,
    ca.TotalCompra,
    ca.Estatus,
    ca.Fecha,
    cd.Cantidad AS CantidadComprada,
    cd.Subtotal,
    p.Descripcion AS Producto,
    p.Precio AS PrecioUnitario
FROM Carrito ca
INNER JOIN CarritoDetalle cd ON ca.Id = cd.IdCarrito
INNER JOIN Productos p ON cd.IdProducto = p.Id;

-- CONSULTA 3: Carritos con detalles, productos, marcas y categorías
SELECT 
    ca.FolioVenta,
    ca.Estatus,
    ca.Fecha,
    ca.TotalCompra,
    p.Descripcion AS Producto,
    p.Precio AS PrecioUnitario,
    cd.Cantidad AS CantidadComprada,
    cd.Subtotal,
    m.Descripcion AS Marca,
    c.Descripcion AS Categoria
FROM Carrito ca
INNER JOIN CarritoDetalle cd ON ca.Id = cd.IdCarrito
INNER JOIN Productos p ON cd.IdProducto = p.Id
INNER JOIN Marcas m ON p.IdMarca = m.Id
INNER JOIN Categorias c ON p.IdCategoria = c.Id;
