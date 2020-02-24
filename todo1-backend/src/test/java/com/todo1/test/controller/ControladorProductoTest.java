package com.todo1.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo1.model.Categoria;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorProductoTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    ProductoTest p;
  //private static final Integer ID_PRODUCTO= 1;
  	private static final String NOMBRE = "Martillo de Thor";
  	private static final Double PRECIO = 250000.00;
  	private static final Integer STOCK = 10;
  	private static final String IMG =  "imagen.jpg";
  	private static final Categoria categoria  = new Categoria();
  	
  	

    @Test
    public void getProductoPorId() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/productos/{id}", 4)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idProducto").value(4));
    }

    @Test
    public void crearProducto() throws Exception
    {
        p = new ProductoTest();
        p.setNombre(NOMBRE);
        p.setPrecio(PRECIO);
        p.setStock(STOCK);
        p.setImg(IMG);
        p.setEnabled(true);
        
        categoria.setIdCategoria(4);
        categoria.setNombre("juguetes");
        p.setCategoria(categoria);
        
        
        mvc.perform( MockMvcRequestBuilders
                .post("/productos")
                .content(objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    
    }
}
