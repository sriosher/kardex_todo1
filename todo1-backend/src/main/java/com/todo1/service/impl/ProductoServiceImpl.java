package com.todo1.service.impl;
/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.todo1.model.Producto;
import com.todo1.repo.IProductoRepo;
import com.todo1.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoRepo repo;
	
	@Override
	public Producto registrar(Producto producto) {
		return repo.save(producto);	
	}

	@Override
	public Producto modificar(Producto producto) {
		return repo.save(producto);
	}

	@Override
	public List<Producto> listar() {
		return repo.findAll();
	}

	@Override
	public Producto leerPorId(Integer id) {
		Optional<Producto> obj = repo.findById(id);
		return obj.isPresent() ? obj.get() : new Producto();	
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

	@Override
	public Page<Producto> listarPageable(Pageable pageable) {
		
		return repo.findAll(pageable);
	}

}
