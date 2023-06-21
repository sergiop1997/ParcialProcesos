package com.Productos.productos.Service;

import com.Productos.productos.Models.Productos;
import com.Productos.productos.Repository.ProductoRepository;
import com.Productos.productos.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class ProductoServiceImp implements ProductoService{

    private final RestTemplate restTemplate;
    private  ProductoRepository productorepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public ProductoServiceImp(RestTemplate restTemplate, ProductoRepository productorepository) {
        this.restTemplate = restTemplate;
        this.productorepository = productorepository;
    }


    @Override
    public Productos getProducto(Long id){
        return productorepository.findById(id).get();
    }

    @Override
    public List<Productos> allProducto() {
        return productorepository.findAll();
    }

    @Override
    public Boolean createProducto(Long id, Long id_user)throws JsonProcessingException {
        String urlApi = "https://6441aadb76540ce2257c3dfc.mockapi.io/product/"+id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> producto = restTemplate.getForEntity(urlApi, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Productos product = objectMapper.readValue(producto.getBody(), Productos.class);
        System.out.println(product.getPrecio());
        if(productorepository.findById(id).isEmpty() && !userRepository.findById(id_user).isEmpty()){
            product.setUser(userService.getUser(id_user));
            productorepository.save(product);
            return true;
        }
        return false;
    }
    @Override
    public Boolean delete(Long id){
        try {
            Productos product = productorepository.findById(id).get();
            productorepository.delete(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public Boolean updateProducto(Long id, Productos productos) {
        try {
            Productos productosBD = productorepository.findById(id).get();
            productosBD.setTitulo(productos.getTitulo());
            productosBD.setDescripcion(productos.getDescripcion());
            productosBD.setPrecio(productos.getPrecio());
            productosBD.setImagen(productos.getImagen());
            productosBD.setCategoria(productos.getCategoria());
            Productos productosUp = productorepository.save(productosBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}