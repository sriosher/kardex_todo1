package com.todo1.test.controller;

public class CategoriaTest {
	
	private Integer idCategoria;
	private String nombre;
	
	public CategoriaTest(Integer idCategoria, String nombre) {
		
		this.idCategoria = idCategoria;
		this.nombre = nombre;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	

}
