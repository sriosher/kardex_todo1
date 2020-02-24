package com.todo1.repo;

/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo1.model.Venta;

@Repository
public interface IVentaRepo extends JpaRepository<Venta, Integer>{

}
