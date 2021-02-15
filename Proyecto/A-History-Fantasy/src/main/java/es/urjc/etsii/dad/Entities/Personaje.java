package es.urjc.etsii.dad.Entities;

import javax.persistence.*;

@Entity
public class Personaje {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private String Nombre;
	private int Rango;
	private int Tipo;
	private int Precio;
	
	private int vMilitar;
	private int vDiplomatico;
	private int vCultural;
}
