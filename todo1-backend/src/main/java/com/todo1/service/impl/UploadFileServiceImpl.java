package com.todo1.service.impl;
/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todo1.service.IUploadFileService;



@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final static String DIRECTORIO_UPLOAD = ".//src//main//resources//uploads//";

	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		Path rutaArchivo = getPath(nombreFoto);
		Resource recurso = null;
			recurso = (Resource) new UrlResource(rutaArchivo.toUri());
		if(!recurso.exists() && !recurso.isReadable()) {
			 rutaArchivo = Paths.get(".//src//main//resources//uploads//").resolve("not-img.png").toAbsolutePath();
			 recurso = (Resource) new UrlResource(rutaArchivo.toUri());
			//throw new ModeloNotFoundException("Error al cargar la imagen" + nombreFoto);
		}
		
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(nombreArchivo);
					
			Files.copy(archivo.getInputStream(), rutaArchivo);
	
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFotoAnterior) {
		if(nombreFotoAnterior != null && nombreFotoAnterior.length() > 0 ) {
			Path rutaFotoAnterior = Paths.get(".//src//main//resources//uploads//").resolve(nombreFotoAnterior).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {

		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
		
	}

}
