package com.pgl8.sherryguia.models;

import java.util.Date;

/**
 * Clase para dar forma a los comentarios de la app.
 */

public class Comentario {
	private String usuario;
	private String texto;
	private String fecha;
	private int rating;

	public Comentario(String usuario, String texto, String fecha, int rating) {
		this.usuario = usuario;
		this.texto = texto;
		this.fecha = fecha;
		this.rating = rating;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Comentario{" +
				"usuario='" + usuario + '\'' +
				", texto='" + texto + '\'' +
				", fecha='" + fecha + '\'' +
				", rating=" + rating +
				'}';
	}
}
