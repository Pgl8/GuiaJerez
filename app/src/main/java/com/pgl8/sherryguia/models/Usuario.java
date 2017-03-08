package com.pgl8.sherryguia.models;

/**
 * Clase para dar forma a los usuarios de la app.
 */

public class Usuario {
	private String nombre;

	// Constructor
	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	// Getter y Setter
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
