package com.servidorInterno.HistoryFantasy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String nombre;
	private String contrasenaHash;
	private long dinero;
	private long puntos;
	private boolean baneado;
	
	@ElementCollection(fetch = FetchType.EAGER)
	 private List<String> roles;

	@OneToOne(cascade= CascadeType.ALL)
	private Formacion formacion;
	
	public User() {
		setDinero(0);
		setPuntos(0);
	}
	public User(String nombre, String contrasena) {
		this.nombre= nombre;
		this.contrasenaHash= contrasena;
		roles= new ArrayList<String>();
	}
	public void setFormacion(Formacion f) {
		formacion = f;
	}
	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
	public void setContrasena(String contrasena) {
		this.contrasenaHash= contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public String getContrasena() {
		return contrasenaHash;
	}
	public Formacion getFormacion() {
		return formacion;
	}
	public long getDinero() {
		return dinero;
	}
	public void setDinero(long dinero) {
		this.dinero = dinero;
	}
	public long getPuntos() {
		return puntos;
	}
	public void setPuntos(long puntos) {
		this.puntos = puntos;
	}
	public boolean isBaneado() {
		return baneado;
	}
	public void setBaneado(boolean baneado) {
		this.baneado = baneado;
	}
	public void addRol(String rol) {
		roles.add(rol);
	}
	public List<String> getRoles(){
		return roles;
	}
	
}

