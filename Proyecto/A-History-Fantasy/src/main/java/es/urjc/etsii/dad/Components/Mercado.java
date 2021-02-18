package es.urjc.etsii.dad.Components;

import java.util.List;

import javax.persistence.*;

@Entity
public class Mercado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany
	private List<Personaje> oferta;
}
