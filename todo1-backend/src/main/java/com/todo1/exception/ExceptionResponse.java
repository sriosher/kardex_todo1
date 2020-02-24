package com.todo1.exception;
/**
 * Version: 1.0
 * Fecha: 24/02/2020
 * @author: Saul Rios 
 */
 
import java.time.LocalDateTime;

public class ExceptionResponse {

	private LocalDateTime timestamp;
	private String mensaje;
	private String detalle;

	public ExceptionResponse(LocalDateTime timestamp, String mensaje, String detalle) {
		super();
		this.timestamp = timestamp;
		this.mensaje = mensaje;
		this.detalle = detalle;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

}
