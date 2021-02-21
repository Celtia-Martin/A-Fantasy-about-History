package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Random;

import javax.persistence.*;

import es.urjc.etsii.dad.Components.Enums.*;

@Entity
public class Formacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Personaje> personajes;
	@OneToOne(mappedBy="formacion")
	private User propietario;
	
	public Formacion() {
		
		String nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.MILITAR, 1000, 200, 100, 100));
		
		nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.MILITAR, 1000, 200, 100, 100));
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 1000, 100, 200, 100));
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 1000, 100, 200, 100));
		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.CULTURAL, 1000, 100, 100, 200));
		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		personajes.add(new Personaje(nom, 1, TipoBatalla.CULTURAL, 1000, 100, 100, 200));
		
	}
	
	private String RandomizarPersonaje(TipoBatalla TB) {
		
		int a = (int) (Math.random() * 9.99);
		
		Nacion nac = Nacion.values()[a];
		
		return TB.toString() + " " + nac;
	}
}


