package com.miempresa.middleware.repository;

import com.miempresa.middleware.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Spring Data JPA crea automáticamente las consultas CRUD
}