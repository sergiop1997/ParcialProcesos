package com.Productos.productos.Service;
import com.Productos.productos.Models.Productos;

import java.util.List;

public interface ProductoService {
    Productos getProducto(Long id);
    List<Productos> allProducto();
    Boolean createProducto();
    Boolean updateProducto(Long id,Productos productos);
}