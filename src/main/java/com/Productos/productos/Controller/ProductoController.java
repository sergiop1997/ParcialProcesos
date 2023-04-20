package com.Productos.productos.Controller;

import com.Productos.productos.Models.Productos;
import com.Productos.productos.Service.ProductoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductoController {

    @Autowired
    private ProductoServiceImp productoServiceImp;

    @GetMapping(value = "/producto/{id}")
    public ResponseEntity findProductoById(@PathVariable Long id){

        Map response = new HashMap();
        try {
            return new ResponseEntity(productoServiceImp.getProducto(id), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontro el producto!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/producto")
    public ResponseEntity findAllProducto(){
        Map response = new HashMap();
        try {
            return new ResponseEntity(productoServiceImp.allProducto(), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontraron los productos!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/producto")
    public ResponseEntity saveProducto(){
        Map response = new HashMap();
        Boolean userResp = productoServiceImp.createProducto();

        if(userResp == true){
            response.put("status", "201");
            response.put("message","Se creo el Producto");
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        response.put("status","400");
        response.put("message","Hubo un error al crear el Producto");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/producto/{id}")

    public ResponseEntity updateAutomovil(@PathVariable Long id, @RequestBody Productos productos){
        Map response = new HashMap();
        Boolean userResp = productoServiceImp.updateProducto(id,productos);

        if(userResp == true){
            response.put("status", "200");
            response.put("message","Se actualizo el vehiculo");
            return new ResponseEntity(response, HttpStatus.OK);
        }
        response.put("status","400");
        response.put("message","Hubo un error al actualizar el vehiculo");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

}
