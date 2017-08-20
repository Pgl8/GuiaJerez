package com.pgl8.sherryguia.models;


/**
 * Clase para dar forma a los comentarios de la app.
 */

public class Comentario {
	private String vino;
	private String comentario;
	private float puntuacion;
	private String fecha;
	private String usuario;

	public Comentario(String vino, String comentario, float puntuacion, String fecha, String usuario){
		super();
		this.setVino(vino);
		this.setComentario(comentario);
		this.setPuntuacion(puntuacion);
		this.setFecha(fecha);
		this.setUsuario(usuario);
	}

	@Override
	public String toString() {
		return "Comentario{" +
				"vino='" + vino + '\'' +
				", comentario='" + comentario + '\'' +
				", puntuacion=" + puntuacion +
				", fecha='" + fecha + '\'' +
				", usuario='" + usuario + '\'' +
				'}';
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getVino() {
		return vino;
	}

	public void setVino(String vino) {
		this.vino = vino;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
