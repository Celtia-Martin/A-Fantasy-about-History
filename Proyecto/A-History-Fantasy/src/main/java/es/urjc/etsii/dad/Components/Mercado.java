package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Mercado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Personaje> oferta=  new ArrayList<>();
	
	public Mercado() {
		
	}
	public void addPersonajes(List<Personaje> personajes) {
		oferta.addAll(personajes);
	}

	public List<Personaje> getOferta(){
		return oferta;
	}
}
