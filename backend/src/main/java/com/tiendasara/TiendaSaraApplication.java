package com.tiendasara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * {@code @SpringBootApplication} habilita auto-configuración, escaneo de componentes y configuración.
 */
@SpringBootApplication
public class TiendaSaraApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaSaraApplication.class, args);
    }
}
