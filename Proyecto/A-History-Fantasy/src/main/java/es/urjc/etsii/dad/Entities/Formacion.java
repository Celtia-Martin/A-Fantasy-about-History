package es.urjc.etsii.dad.Entities;

import javax.persistence.*;

@Entity
public class Formacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//private Personaje [] formacion;
}
