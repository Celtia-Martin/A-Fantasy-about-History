package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

import es.urjc.etsii.dad.Components.Enums.*;

@Entity
public class Formacion { //al eliminar un default se elimina tambien de la base de datos, RECORDAR
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Personaje> personajes= new ArrayList<>();
	
	@OneToOne(mappedBy="formacion")
	private User propietario;
	
	public Formacion() {
		
	}
	public void initFormacion(ControlPersonajes control) {
		
		String nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		Personaje p=new Personaje(nom, 1, TipoBatalla.MILITAR, 1000, 200, 100, 100,true);
		control.addDefault(p);
		personajes.add(p);

		
		
		nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		p= new Personaje(nom, 1, TipoBatalla.MILITAR, 1000, 200, 100, 100,true);
		control.addDefault(p);
		personajes.add(p);
		
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		p= new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 1000, 100, 200, 100,true);
		control.addDefault(p);
		personajes.add(p);
	
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		p= new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 1000, 100, 200, 100,true);
		control.addDefault(p);
		personajes.add(p);
		
		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		p=new Personaje(nom, 1, TipoBatalla.CULTURAL, 1000, 100, 100, 200,true);
		control.addDefault(p);
		personajes.add(p);

		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		p= new Personaje(nom, 1, TipoBatalla.CULTURAL, 1000, 100, 100, 200,true);
		control.addDefault(p);
		personajes.add(p);
		
	

	}
	
	public void SetFormationToPersonaje() {
		for(Personaje p: personajes) {
			p.setFormacion(this);
		}
	}
	public void setPropietario(User user) {
		propietario= user;
	}
	private String RandomizarPersonaje(TipoBatalla TB) {
		
		int a = (int) (Math.random() * 9.99);
		
		Nacion nac = Nacion.values()[a];
		
		return TB.toString() + " " + nac;
	}
	public List<Personaje> getPersonajes(){
		return personajes;
	}
}


