package com.tiendasara.controllers;

import com.tiendasara.models.Category;
import com.tiendasara.models.Mark;
import com.tiendasara.models.Product;
import com.tiendasara.services.CategoryRepository;
import com.tiendasara.services.MarkRepository;
import com.tiendasara.services.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.math.BigDecimal;

/**
 * REST API Controller para Productos.
 * Endpoints JSON consumidos por el frontend estático.
 */
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MarkRepository markRepository;

    public ProductApiController(ProductRepository productRepository,
                                CategoryRepository categoryRepository,
                                MarkRepository markRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.markRepository = markRepository;
    }

    // ── GET ALL ──────────────────────────────────────────────
    @GetMapping("")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ── GET BY ID ────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── CREATE ───────────────────────────────────────────────
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        try {
            String descripcion = (String) body.get("descripcion");
            BigDecimal precio = new BigDecimal(body.get("precio").toString());
            int cantidad = Integer.parseInt(body.get("cantidad").toString());
            UUID idCategoria = UUID.fromString((String) body.get("idCategoria"));
            UUID idMarca = UUID.fromString((String) body.get("idMarca"));

            Category category = categoryRepository.findById(idCategoria).orElse(null);
            Mark mark = markRepository.findById(idMarca).orElse(null);

            if (category == null || mark == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Categoría o Marca no válida"));
            }

            Product product = new Product();
            product.setDescripcion(descripcion);
            product.setPrecio(precio);
            product.setCantidad(cantidad);
            product.setCategory(category);
            product.setMark(mark);

            Product saved = productRepository.save(product);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ── UPDATE ───────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Map<String, Object> body) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            if (body.containsKey("descripcion")) product.setDescripcion((String) body.get("descripcion"));
            if (body.containsKey("precio")) product.setPrecio(new BigDecimal(body.get("precio").toString()));
            if (body.containsKey("cantidad")) product.setCantidad(Integer.parseInt(body.get("cantidad").toString()));

            if (body.containsKey("idCategoria")) {
                Category cat = categoryRepository.findById(UUID.fromString((String) body.get("idCategoria"))).orElse(null);
                if (cat != null) product.setCategory(cat);
            }
            if (body.containsKey("idMarca")) {
                Mark mark = markRepository.findById(UUID.fromString((String) body.get("idMarca"))).orElse(null);
                if (mark != null) product.setMark(mark);
            }

            Product saved = productRepository.save(product);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ── DELETE ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.ok(Map.of("message", "Producto eliminado"));
    }
}
