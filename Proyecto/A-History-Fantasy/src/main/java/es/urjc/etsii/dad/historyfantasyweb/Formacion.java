package es.urjc.etsii.dad.historyfantasyweb;

import javax.persistence.*;

@Entity
public class Formacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int ID;
	private Personaje [] formacion;
}
