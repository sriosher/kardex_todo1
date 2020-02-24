/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
package com.todo1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.todo1.exception.ModeloNotFoundException;
import com.todo1.model.Categoria;
import com.todo1.service.ICategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> listar() {
		List<Categoria> lista_categoria = service.listar();
		return new ResponseEntity<List<Categoria>>(lista_categoria, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> leerPorId(@PathVariable("id") Integer id) {
		Categoria categoria = service.leerPorId(id);

		if (categoria.getIdCategoria() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id); 
		}
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Categoria categoria) {
		Categoria obj = service.registrar(categoria);
		// categorias/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdCategoria()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Categoria> modificar(@Valid @RequestBody Categoria categoria) {
		Categoria obj = service.modificar(categoria);
		return new ResponseEntity<Categoria>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Categoria categoria = service.leerPorId(id);
		if (categoria.getIdCategoria() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id); 
		}
		service.eliminar(id);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}


	

}
