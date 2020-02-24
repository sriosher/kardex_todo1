package com.todo1.service;

import java.util.List;
/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */


public interface ICRUD<T> {

	T registrar(T obj);

	T modificar(T obj);

	List<T> listar();

	T leerPorId(Integer id);

	boolean eliminar(Integer id);
}
