package com.Productos.productos.Service;
import com.Productos.productos.Models.Productos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductoService {
    Productos getProducto(Long id);
    List<Productos> allProducto();
    Boolean createProducto(Long id, Long id_user) throws JsonProcessingException;
    Boolean updateProducto(Long id,Productos productos);
    Boolean delete(Long id);
}