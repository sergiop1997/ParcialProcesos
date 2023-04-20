package com.Productos.productos.Repository;

import com.Productos.productos.Models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Productos, Long> {
}
