package com.todo1.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.todo1.model.DetalleVenta;
import com.todo1.model.Usuario;
import com.todo1.model.Venta;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorVentaTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    Venta venta;
    List<DetalleVenta> listadetalleVenta;
    DetalleVenta detalleVenta;
    Usuario usuario;
    
  	
  	
    @Test
    public void getVentaPorId() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/ventas/{id}", 3)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idVenta").value(3));
    }

    @Test
    public void crearVenta() throws Exception
    {
    	usuario = new Usuario();
    	usuario.setIdUsuario(1);
    	detalleVenta = new DetalleVenta();
    	detalleVenta.setIdProducto(4);
    	detalleVenta.setCantidad(1);
    	detalleVenta.setSubtotal(150000.00);
    	listadetalleVenta = new ArrayList<DetalleVenta>();
    	listadetalleVenta.add(detalleVenta);
    	
        venta = new Venta();
        venta.setUsuario(usuario);
        venta.setTotal(150000.00);
        venta.setDetalleVenta(listadetalleVenta);
       
        mvc.perform( MockMvcRequestBuilders
                .post("/ventas")
                .content(objectMapper.writeValueAsString(venta))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    
    }
}
