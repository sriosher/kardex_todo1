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


import com.todo1.model.Venta;
import com.todo1.repo.IVentaRepo;
import com.todo1.service.IVentaService;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private IVentaRepo repo;

	@Override
	public Venta registrar(Venta venta) {
		venta.getDetalleVenta().forEach(det -> {
			det.setVenta(venta);
		});
		
		return repo.save(venta);
		
	}

	@Override
	public Venta modificar(Venta venta) {
		return repo.save(venta);
	}

	@Override
	public List<Venta> listar() {

		return repo.findAll();
	}

	@Override
	public Venta leerPorId(Integer id) {
		Optional<Venta> obj = repo.findById(id);
		return obj.isPresent() ? obj.get() : new Venta();
	}

	@Override
	public boolean eliminar(Integer id) {

		repo.deleteById(id);
		return true;
	}

}
