package com.pgl8.sherryguia.models;

/**
 * Clase para dar forma a los vinos de la app.
 */

public class Vino {
	private int id;
	private String nombre;
	private String descripcion;
	private String tipo_uva;
	private String graduacion;
	private String tipo_vino;
	private String contenido;
	private String elaboracion;
	private String nota_cata;
	private String consumo;
	private String vinedo;
	private String url;
	private String image;

	public Vino(int id, String nombre, String descripcion, String tipoUva, String graduacion, String tipo, String contenido, String elaboracion, String notaCata, String consumo, String vinedo, String url, String image) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo_uva = tipoUva;
		this.graduacion = graduacion;
		this.tipo_vino = tipo;
		this.contenido = contenido;
		this.elaboracion = elaboracion;
		this.nota_cata = notaCata;
		this.consumo = consumo;
		this.vinedo = vinedo;
		this.url = url;
		this.image = image;
	}

	// Getters
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getTipoUva() {
		return tipo_uva;
	}
	public String getGraduacion() {
		return graduacion;
	}
	public String getTipo() {
		return tipo_vino;
	}
	public String getContenido() {
		return contenido;
	}
	public String getElaboracion() {
		return elaboracion;
	}
	public String getNotaCata() {
		return nota_cata;
	}
	public String getConsumo() {
		return consumo;
	}
	public String getVinedo() {
		return vinedo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
