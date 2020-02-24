package com.todo1.service.impl;
/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.todo1.model.Categoria;
import com.todo1.repo.ICategoriaRepo;
import com.todo1.service.ICategoriaService;

@Service
public class CategoriaServicesImpl implements ICategoriaService {

	@Autowired
	private ICategoriaRepo repo;

	@Override
	public Categoria registrar(Categoria categoria) {
		return repo.save(categoria);
	}

	@Override
	public Categoria modificar(Categoria categoria) {
		return repo.save(categoria);
	}

	@Override
	public List<Categoria> listar() {
		return repo.findAll();
	}

	@Override
	public Categoria leerPorId(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.isPresent() ? obj.get() : new Categoria();
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

}
