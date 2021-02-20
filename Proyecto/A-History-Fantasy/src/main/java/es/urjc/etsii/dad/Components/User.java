package es.urjc.etsii.dad.Components;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nombre;
	private String contrasena;
	@OneToOne(cascade= CascadeType.ALL)
	private Formacion formacion;
	
	public User(String nombre, String contrasena) {
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
	
}

