package com.servidorInterno.HistoryFantasy;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

import com.servidorInterno.HistoryFantasy.Enums.*;

@Entity
public class Formacion { //al eliminar un default se elimina tambien de la base de datos, RECORDAR
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Personaje> personajes= new ArrayList<>();//unidireccional
	
	@OneToOne(mappedBy="formacion")//bidireccional
	private User propietario;
	
	public Formacion() {
		
	}
	public void initFormacion(ControlPersonajes control) {
		
		String nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		Personaje p=new Personaje(nom, 1, TipoBatalla.MILITAR, 250, 200, 100, 100,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);

		
		
		nom = RandomizarPersonaje(TipoBatalla.MILITAR);
		
		p= new Personaje(nom, 1, TipoBatalla.MILITAR, 250, 200, 100, 100,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);
		
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		p= new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 250, 100, 200, 100,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);
	
		
		nom = RandomizarPersonaje(TipoBatalla.DIPLOMATICO);
		
		p= new Personaje(nom, 1, TipoBatalla.DIPLOMATICO, 250, 100, 200, 100,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);
		
		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		p=new Personaje(nom, 1, TipoBatalla.CULTURAL, 250, 100, 100, 200,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);

		
		nom = RandomizarPersonaje(TipoBatalla.CULTURAL);
		
		p= new Personaje(nom, 1, TipoBatalla.CULTURAL, 250, 100, 100, 200,true);
		p.setTieneFormacion(true);
		control.addDefault(p);
		personajes.add(p);
		
	

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
	public boolean deletePersonaje(long id) {
		return(personajes.removeIf(p -> p.getId()==id));
	}
	public void addPersonaje(Personaje p) {
		personajes.add(p);
	}
	public Long getId() {
		return id;
	}
	public int contPersonajes() {
		return personajes.size();
	}
	
	
}


