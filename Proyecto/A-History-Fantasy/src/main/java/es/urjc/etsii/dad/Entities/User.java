package es.urjc.etsii.dad.Entities;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String Nombre;
	private String Contrasena;
	
	public User(String nombre, String contrasena) {
		this.Nombre= nombre;
		this.Contrasena= contrasena;
	}
	
	public void setNombre(String nombre) {
		this.Nombre= nombre;
	}
	public void setContrasena(String contrasena) {
		this.Contrasena= contrasena;
	}
	public String getNombre() {
		return Nombre;
	}
	public String getContrasena() {
		return Contrasena;
	}
	
}

