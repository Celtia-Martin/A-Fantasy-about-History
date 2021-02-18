package es.urjc.etsii.dad.Components;

import javax.persistence.*;

@Entity
public class Personaje {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String Nombre;
	private long Rango;
	private long Tipo;
	private long Precio;
	
	private long vMilitar;
	private long vDiplomatico;
	private long vCultural;
	
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
}
