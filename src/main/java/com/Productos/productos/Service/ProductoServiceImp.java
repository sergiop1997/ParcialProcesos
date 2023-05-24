package com.Productos.productos.Service;

import com.Productos.productos.Models.Productos;
import com.Productos.productos.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class ProductoServiceImp implements ProductoService{

    private final RestTemplate restTemplate;
    private  ProductoRepository productorepository;

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
    public Boolean createProducto() {

        try {
            String url="https://6440a1b3fadc69b8e06f45f1.mockapi.io/productos";
            Productos[] response= restTemplate.getForObject(url, Productos[].class);
            productorepository.saveAll(Arrays.asList(response));
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @Override
    public Boolean updateProducto(Long id, Productos productos) {
        try {
            Productos productosBD = productorepository.findById(id).get();
            productosBD.setTitle(productos.getTitle());
            productosBD.setDescription(productos.getDescription());
            productosBD.setPrice(productos.getPrice());
            productosBD.setImage(productos.getImage());
            productosBD.setCategory(productos.getCategory());
            Productos productosUp = productorepository.save(productosBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}