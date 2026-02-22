package com.tiendasara.controllers;

import com.tiendasara.models.Category;
import com.tiendasara.models.Mark;
import com.tiendasara.models.Product;
import com.tiendasara.models.ProductDto;
import com.tiendasara.services.CategoryRepository;
import com.tiendasara.services.MarkRepository;
import com.tiendasara.services.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador MVC para gestión de Productos.
 * Inyección de dependencias por constructor (buena práctica SOLID — Dependency Inversion).
 * Usa DTOs para recibir datos del formulario y entidades para persistir.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MarkRepository markRepository;

    public ProductController(ProductRepository productRepository,
                             CategoryRepository categoryRepository,
                             MarkRepository markRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.markRepository = markRepository;
    }

    // ── LISTAR PRODUCTOS ─────────────────────────────────────

    @GetMapping("")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    // ── CREAR PRODUCTO (mostrar formulario) ──────────────────

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("marks", markRepository.findAll());
        return "products/create";
    }

    // ── CREAR PRODUCTO (procesar formulario) ─────────────────

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/create";
        }

        Category category = categoryRepository.findById(productDto.getIdCategoria()).orElse(null);
        Mark mark = markRepository.findById(productDto.getIdMarca()).orElse(null);

        if (category == null || mark == null) {
            model.addAttribute("error", "Categoría o Marca no válida");
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/create";
        }

        Product product = new Product();
        product.setDescripcion(productDto.getDescripcion());
        product.setPrecio(productDto.getPrecio());
        product.setCantidad(productDto.getCantidad());
        product.setCategory(category);
        product.setMark(mark);

        productRepository.save(product);

        return "redirect:/products";
    }

    // ── EDITAR PRODUCTO (mostrar formulario) ─────────────────

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return "redirect:/products";
        }

        ProductDto productDto = new ProductDto();
        productDto.setDescripcion(product.getDescripcion());
        productDto.setPrecio(product.getPrecio());
        productDto.setCantidad(product.getCantidad());
        productDto.setIdCategoria(product.getCategory().getId());
        productDto.setIdMarca(product.getMark().getId());

        model.addAttribute("product", product);
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("marks", markRepository.findAll());

        return "products/edit";
    }

    // ── EDITAR PRODUCTO (procesar formulario) ────────────────

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable UUID id,
                                @Valid @ModelAttribute("productDto") ProductDto productDto,
                                BindingResult bindingResult,
                                Model model) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return "redirect:/products";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/edit";
        }

        Category category = categoryRepository.findById(productDto.getIdCategoria()).orElse(null);
        Mark mark = markRepository.findById(productDto.getIdMarca()).orElse(null);

        if (category == null || mark == null) {
            model.addAttribute("error", "Categoría o Marca no válida");
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/edit";
        }

        product.setDescripcion(productDto.getDescripcion());
        product.setPrecio(productDto.getPrecio());
        product.setCantidad(productDto.getCantidad());
        product.setCategory(category);
        product.setMark(mark);

        productRepository.save(product);

        return "redirect:/products";
    }

    // ── ELIMINAR PRODUCTO ────────────────────────────────────

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            productRepository.delete(product);
        }

        return "redirect:/products";
    }
}
