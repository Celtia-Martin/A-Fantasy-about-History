package es.urjc.etsii.dad.Components;

import java.util.List;

import javax.persistence.*;

@Entity
public class Batalla {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Enums.TipoBatalla tipo;
	@ManyToMany
	private List<Personaje> participantes;
}
