package es.urjc.etsii.dad.historyfantasyweb;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


public class Usuario {

	private String nombre;
	private String contrasena;
	private String image;
	public Usuario(String nombre, String contrasena) {
		this.nombre= nombre;
		this.contrasena= contrasena;
	}
	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
	public void setContrasena(String contrasena) {
		this.contrasena= contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
