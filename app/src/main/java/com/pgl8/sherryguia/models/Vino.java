package com.pgl8.sherryguia.models;

/**
 * Clase para dar forma a los vinos de la app.
 */

public class Vino {
	private String nombre;
	private String definicion;
	private String tipoUva;
	private String graduacion;
	private String tipo;
	private String contenidoAz;
	private String elaboracion;
	private String notaCata;
	private String consumo;

	public Vino(String nombre, String definicion, String tipoUva, String graduacion, String tipo, String contenidoAz, String elaboracion, String notaCata, String consumo) {
		this.nombre = nombre;
		this.definicion = definicion;
		this.tipoUva = tipoUva;
		this.graduacion = graduacion;
		this.tipo = tipo;
		this.contenidoAz = contenidoAz;
		this.elaboracion = elaboracion;
		this.notaCata = notaCata;
		this.consumo = consumo;
	}

	// Getters
	public String getNombre() {
		return nombre;
	}
	public String getDefinicion() {
		return definicion;
	}
	public String getTipoUva() {
		return tipoUva;
	}
	public String getGraduacion() {
		return graduacion;
	}
	public String getTipo() {
		return tipo;
	}
	public String getContenidoAz() {
		return contenidoAz;
	}
	public String getElaboracion() {
		return elaboracion;
	}
	public String getNotaCata() {
		return notaCata;
	}
	public String getConsumo() {
		return consumo;
	}
}
