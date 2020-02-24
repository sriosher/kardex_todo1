/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
package com.todo1.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo1.exception.ModeloNotFoundException;
import com.todo1.model.Producto;
import com.todo1.service.IProductoService;
import com.todo1.service.IUploadFileService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService service;
	
	@Autowired
	private IUploadFileService uploadService;
	
	@GetMapping
	public ResponseEntity<List<Producto>> listar(){
		List<Producto> productos = service.listar();
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Producto>> listarPageable(Pageable pageable) {
		Page<Producto> productos = service.listarPageable(pageable);
		return new ResponseEntity<Page<Producto>>(productos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> leerPorId(@PathVariable("id") Integer id) {
		Producto producto = service.leerPorId(id);
		if(producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
			
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Producto producto) {
	
		Producto obj = service.registrar(producto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdProducto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
		
	@PutMapping
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto producto) {
		Producto obj = service.modificar(producto);
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		
		Producto producto = service.leerPorId(id);
		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id); 
		}
		
		String nombreFotoAnterior = producto.getImg();
		
		uploadService.eliminar(nombreFotoAnterior);
		
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Integer id){
		Map<String, Object> response = new HashMap<>();
		Producto producto = service.leerPorId(id);
		
		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id); 
		}
		
		if(!archivo.isEmpty()) {
			
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat(": " ).concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String nombreFotoAnterior = producto.getImg();
			uploadService.eliminar(nombreFotoAnterior);
			
			producto.setImg(nombreArchivo);
			service.registrar(producto);
			response.put("Especialidad", producto);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
			
			
		}
		
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
			
	}
	
	@GetMapping("upload/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) throws MalformedURLException{
		
		Resource recurso = null;
		recurso = uploadService.cargar(nombreFoto);
		
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"" );
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
		
		
	}

	

}
