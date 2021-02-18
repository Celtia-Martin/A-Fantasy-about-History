package es.urjc.etsii.dad.Components;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nombre;
	private String Contrasena;
	@OneToOne
	private Formacion miFormacion;
	
	public User(String nombre, String contrasena) {
		this.nombre= nombre;
		this.Contrasena= contrasena;
	}
	
	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
	public void setContrasena(String contrasena) {
		this.Contrasena= contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public String getContrasena() {
		return Contrasena;
	}
	
}

