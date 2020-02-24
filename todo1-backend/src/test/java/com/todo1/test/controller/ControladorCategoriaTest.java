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
public class ControladorCategoriaTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    Categoria c;
  	private static final String NOMBRE = "Peliculas";
  	
    @Test
    public void getCategoriaPorId() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/categorias/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCategoria").value(1));
    }

    @Test
    public void crearCategoria() throws Exception
    {
        c = new Categoria();
        c.setNombre(NOMBRE);
        
        mvc.perform( MockMvcRequestBuilders
                .post("/categorias")
                .content(objectMapper.writeValueAsString(c))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    
    }
}
