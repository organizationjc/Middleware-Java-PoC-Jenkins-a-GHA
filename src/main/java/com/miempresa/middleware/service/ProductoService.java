package com.miempresa.middleware.service;

import com.miempresa.middleware.exception.RecursoNoEncontradoException;
import com.miempresa.middleware.model.Producto;
import com.miempresa.middleware.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public void eliminarProducto(Long id) {
        Producto producto = obtenerPorId(id); // Reutiliza la validación de existencia
        productoRepository.delete(producto);
    }
}