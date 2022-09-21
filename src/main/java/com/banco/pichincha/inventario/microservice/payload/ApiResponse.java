package com.banco.pichincha.inventario.microservice.payload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer codMensaje;

	private List<String> mensajes;

	private List<HashMap<String, String>> result;

	public ApiResponse() {
		this.mensajes = new ArrayList<String>();
	}

	public ApiResponse(Integer codMensaje, String mensaje) {
		this.codMensaje = codMensaje;
		if (this.mensajes == null)
			this.mensajes = new ArrayList<String>();
		this.mensajes.add(mensaje);
	}

	public ApiResponse(Integer codMensaje, String mensaje, Object... objs) {
		this.codMensaje = codMensaje;
		if (this.mensajes == null)
			this.mensajes = new ArrayList<String>();
		this.mensajes.add(mensaje);
		for (Object obj : objs) {
		}

	}

	public Integer getCodMensaje() {
		return codMensaje;
	}

	public void setCodMensaje(Integer codMensaje) {
		this.codMensaje = codMensaje;
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

	public List<HashMap<String, String>> getResult() {
		return result;
	}

	public void setResult(List<HashMap<String, String>> result) {
		this.result = result;
	}

}
