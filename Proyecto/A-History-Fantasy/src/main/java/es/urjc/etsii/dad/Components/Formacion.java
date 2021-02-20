package es.urjc.etsii.dad.Components;

import java.util.List;

import javax.persistence.*;

@Entity
public class Formacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Personaje> personajes;
	@OneToOne(mappedBy="formacion")
	private User propietario;
}
