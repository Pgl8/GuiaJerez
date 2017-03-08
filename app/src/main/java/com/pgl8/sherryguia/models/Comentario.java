package com.pgl8.sherryguia.models;

/**
 * Clase para dar forma a los comentarios de la app.
 */

public class Comentario {
	private String date;
	private String usuario;
	private String texto;

	public Comentario(String date, String usuario, String texto) {
		this.date = date;
		this.usuario = usuario;
		this.texto = texto;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
}
