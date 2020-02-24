package com.todo1.service;

import java.util.List;

import com.todo1.model.Menu;


/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
public interface IMenuService extends ICRUD<Menu>{
	
	List<Menu> listarMenuPorUsuario(String nombre);
}
