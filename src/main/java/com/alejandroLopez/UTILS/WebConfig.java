package com.alejandroLopez.UTILS;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configura Spring para servir archivos desde la carpeta 'photos'
        registry.addResourceHandler("/photos/**")
                .addResourceLocations("file:/C:/Users/FA506/Desktop/ProyectoPrivado/ProyectoSeguros/backen/ProtallerBack/src/main/java/com/alejandroLopez/photos/");
    }
}
