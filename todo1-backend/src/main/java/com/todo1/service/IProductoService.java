package com.todo1.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo1.model.Producto;

/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
public interface IProductoService extends ICRUD<Producto>{
	Page<Producto> listarPageable(Pageable pageable);

}
