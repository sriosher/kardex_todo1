package com.todo1.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
public interface IUploadFileService {
	
	public Resource cargar(String nombreFoto) throws MalformedURLException;
	public String copiar(MultipartFile archivo) throws IOException;
	public boolean eliminar(String nombreFotoAnterior);
	public Path getPath(String nombreFoto);
}
