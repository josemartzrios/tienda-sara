package com.tiendasara.controllers;

import com.tiendasara.models.Mark;
import com.tiendasara.services.MarkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller para Marcas.
 */
@RestController
@RequestMapping("/api/marks")
public class MarkApiController {

    private final MarkRepository markRepository;

    public MarkApiController(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @GetMapping("")
    public List<Mark> getAll() {
        return markRepository.findAll();
    }
}
