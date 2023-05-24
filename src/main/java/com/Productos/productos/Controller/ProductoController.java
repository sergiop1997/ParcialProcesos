package com.Productos.productos.Controller;

import com.Productos.productos.Models.Productos;
import com.Productos.productos.Service.ProductoServiceImp;
import com.Productos.productos.Utils.JWTUtil;
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
    @Autowired
    private JWTUtil jwtUtil;

    private Boolean validateToken(String token){
        try{
            if(jwtUtil.getKey(token) != null){
                return true;
            }
            return  false;
        }catch (Exception e){
            return  false;
        }
    }

    @GetMapping(value = "/producto/{id}")
    public ResponseEntity findProductoById(@PathVariable Long id,@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}
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
    public ResponseEntity findAllProducto(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}
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
    public ResponseEntity saveProducto(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}
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

    public ResponseEntity updateAutomovil(@PathVariable Long id, @RequestBody Productos productos,@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}
        Map response = new HashMap();
        Boolean userResp = productoServiceImp.updateProducto(id,productos);

        if(userResp == true){
            response.put("status", "200");
            response.put("message","Se actualizo el producto");
            return new ResponseEntity(response, HttpStatus.OK);
        }
        response.put("status","400");
        response.put("message","Hubo un error al actualizar el producto");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

}
