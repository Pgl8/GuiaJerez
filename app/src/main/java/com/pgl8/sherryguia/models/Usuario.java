package com.pgl8.sherryguia.models;

/**
 * Clase para dar forma a los usuarios de la app.
 */

public class Usuario {
	private int id;
	private String nombre;

	// Constructor
	public Usuario(int id, String nombre) {
		this.nombre = nombre;
	}

	// Getter y Setter
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
