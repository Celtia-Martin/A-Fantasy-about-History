package es.urjc.etsii.dad.Entities;

import javax.persistence.*;

@Entity
public class Mercado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Personaje [] opciones;
}
