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


	public Batalla() {
		int random= (int) (Math.random()*Enums.TipoBatalla.values().length);
		tipo= Enums.TipoBatalla.values() [random];
	}
	public Enums.TipoBatalla getTipo() {
		return tipo;
	}
}
